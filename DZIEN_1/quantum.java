import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

/**
 * QuantumDice: decyzja jako superpozycja opcji z wagami, a potem "collapse" do jednej opcji.
 *
 * - superposition = mapa {opcja -> waga}
 * - collapse() = losowanie zgodnie z wagami (jak pomiar)
 * - feedback() = prosta aktualizacja wag (wzmacniamy / osłabiamy)
 * - trace = log decyzji (audit trail)
 */
public class Main {

    public static void main(String[] args) {
        QuantumDice<String> qd = QuantumDice.<String>builder()
                .add("EMAIL_CLIENT", bd("0.35"))
                .add("WRITE_PROPOSAL", bd("0.25"))
                .add("CODE_PROTOTYPE", bd("0.30"))
                .add("TAKE_WALK", bd("0.10"))
                .seed(42L) // deterministyczne demo na prezentację
                .build();

        System.out.println("Initial superposition:");
        System.out.println(qd.prettyState());

        // 1) Collapse: wybierz jedną akcję
        Decision<String> d1 = qd.collapse("Morning planning");
        System.out.println("\nCollapse #1:");
        System.out.println(d1);

        // 2) Feedback: załóżmy, że wynik był dobry (nagroda)
        qd.feedback(d1.choice(), +1.0, "Worked well, high leverage");
        System.out.println("\nAfter positive feedback:");
        System.out.println(qd.prettyState());

        // 3) Kolejny collapse po aktualizacji
        Decision<String> d2 = qd.collapse("Next step");
        System.out.println("\nCollapse #2:");
        System.out.println(d2);

        // 4) Feedback: wynik słaby (kara)
        qd.feedback(d2.choice(), -1.0, "Low ROI / wrong timing");
        System.out.println("\nAfter negative feedback:");
        System.out.println(qd.prettyState());

        // 5) Trace (ślady pomiarów)
        System.out.println("\nTrace:");
        qd.trace().forEach(System.out::println);
    }

    private static BigDecimal bd(String s) {
        return new BigDecimal(s);
    }
}

/** Pojedynczy "pomiar": wybrana opcja plus kontekst i snapshot rozkładu. */
record Decision<T>(
        T choice,
        String context,
        Instant timestamp,
        Map<T, BigDecimal> priorDistribution,
        BigDecimal rollPoint // gdzie "wypadło" na osi [0,1)
) {}

final class QuantumDice<T> {

    private final LinkedHashMap<T, BigDecimal> weights; // stan (superpozycja)
    private final Random rng;
    private final List<String> trace;
    private final BigDecimal learningRate; // jak mocno aktualizujemy (prosto, ale działa)

    private QuantumDice(LinkedHashMap<T, BigDecimal> weights, long seed, BigDecimal learningRate) {
        this.weights = new LinkedHashMap<>(weights);
        this.rng = new Random(seed);
        this.trace = new ArrayList<>();
        this.learningRate = learningRate;
        normalizeInPlace();
        trace.add(ts() + " INIT  " + oneLineState());
    }

    /** Collapse = losowanie proporcjonalnie do wag. */
    public Decision<T> collapse(String context) {
        Map<T, BigDecimal> prior = snapshot();

        BigDecimal r = BigDecimal.valueOf(rng.nextDouble()); // [0,1)
        BigDecimal cdf = BigDecimal.ZERO;

        T chosen = null;
        for (var e : weights.entrySet()) {
            cdf = cdf.add(e.getValue());
            if (r.compareTo(cdf) < 0) {
                chosen = e.getKey();
                break;
            }
        }
        if (chosen == null) { // awaryjnie przez rounding
            chosen = weights.keySet().stream().reduce((a, b) -> b).orElseThrow();
        }

        trace.add(ts() + " COLL  context=\"" + context + "\" r=" + fmt(r) + " -> " + chosen);
        return new Decision<>(chosen, context, Instant.now(), prior, r);
    }

    /**
     * Feedback: wzmacnia / osłabia opcję.
     * reward in [-1..+1] (umownie).
     * Reguła: waga_opcji *= (1 + lr * reward)
     * potem normalizacja.
     */
    public void feedback(T choice, double reward, String reason) {
        BigDecimal w = weights.get(choice);
        if (w == null) throw new IllegalArgumentException("Unknown choice: " + choice);

        BigDecimal factor = BigDecimal.ONE.add(
                learningRate.multiply(BigDecimal.valueOf(reward))
        );

        // zabezpieczenie: nie pozwól zejść do <= 0
        BigDecimal newW = w.multiply(factor);
        if (newW.compareTo(new BigDecimal("0.000001")) < 0) {
            newW = new BigDecimal("0.000001");
        }

        weights.put(choice, newW);
        normalizeInPlace();

        trace.add(ts() + " FBCK  choice=" + choice + " reward=" + reward
                + " reason=\"" + reason + "\" state=" + oneLineState());
    }

    public List<String> trace() {
        return Collections.unmodifiableList(trace);
    }

    public String prettyState() {
        StringBuilder sb = new StringBuilder();
        weights.forEach((k, v) -> sb.append("  - ").append(k).append(": ").append(fmt(v)).append("\n"));
        return sb.toString();
    }

    /* ---------- builder ---------- */

    public static <T> Builder<T> builder() { return new Builder<>(); }

    public static final class Builder<T> {
        private final LinkedHashMap<T, BigDecimal> w = new LinkedHashMap<>();
        private long seed = System.nanoTime();
        private BigDecimal learningRate = new BigDecimal("0.20"); // domyślnie

        public Builder<T> add(T option, BigDecimal weight) {
            if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Weight must be > 0");
            }
            w.put(option, weight);
            return this;
        }

        public Builder<T> seed(long seed) {
            this.seed = seed;
            return this;
        }

        public Builder<T> learningRate(BigDecimal lr) {
            if (lr.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("lr must be > 0");
            this.learningRate = lr;
            return this;
        }

        public QuantumDice<T> build() {
            if (w.isEmpty()) throw new IllegalStateException("Add at least one option");
            return new QuantumDice<>(w, seed, learningRate);
        }
    }

    /* ---------- helpers ---------- */

    private void normalizeInPlace() {
        BigDecimal sum = weights.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalStateException("Sum of weights must be > 0");

        for (var e : weights.entrySet()) {
            weights.put(e.getKey(), e.getValue().divide(sum, 12, RoundingMode.HALF_UP));
        }

        // korekta przez rounding: dopnij do 1.0 na ostatnim elemencie
        BigDecimal total = weights.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal diff = BigDecimal.ONE.subtract(total);
        if (diff.abs().compareTo(new BigDecimal("0.000000000001")) > 0) {
            T last = weights.keySet().stream().reduce((a, b) -> b).orElseThrow();
            weights.put(last, weights.get(last).add(diff));
        }
    }

    private Map<T, BigDecimal> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(weights));
    }

    private String oneLineState() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<Map.Entry<T, BigDecimal>> it = weights.entrySet().iterator();
        while (it.hasNext()) {
            var e = it.next();
            sb.append(e.getKey()).append("=").append(fmt(e.getValue()));
            if (it.hasNext()) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private static String fmt(BigDecimal x) {
        return x.setScale(4, RoundingMode.HALF_UP).toPlainString();
    }

    private static String ts() {
        return Instant.now().toString();
    }
}

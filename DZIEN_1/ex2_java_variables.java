import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) {
        int a = 10;
        double b = 2.55567;
        boolean ok = true;
        String msg = String.format("a+b = %.2f, ok = %s",(a+b),ok);
        System.out.println(msg);

        BigDecimal price = new BigDecimal("19.99");
        BigDecimal vat = price.multiply(new BigDecimal("0.23"))
                .setScale(2, RoundingMode.HALF_UP);
        System.out.println(vat);

    }
}

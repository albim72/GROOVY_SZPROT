public class Ex04_FizzBuzz {
    // Cel: pętle, modulo, budowanie stringów
    static String fizzBuzz(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            String out = (i % 15 == 0) ? "FizzBuzz" :
                         (i % 3 == 0)  ? "Fizz" :
                         (i % 5 == 0)  ? "Buzz" : Integer.toString(i);
            sb.append(out).append(i < n ? " " : "");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(fizzBuzz(20));
    }
}

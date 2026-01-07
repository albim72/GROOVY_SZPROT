public class Ex06_Strings {
    // Cel: operacje na String, palindrom, odwracanie
    static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    static boolean isPalindrome(String s) {
        String t = s.replaceAll("\\s+", "").toLowerCase();
        return t.equals(reverse(t));
    }
    public static void main(String[] args) {
        String s = "Kobyła ma mały bok";
        System.out.println("Reverse: " + reverse(s));
        System.out.println("Palindrome? " + isPalindrome(s));
    }
}

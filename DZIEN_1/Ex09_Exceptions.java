class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String msg) { super(msg); }
}

public class Ex09_Exceptions {
    // Cel: wyjątki, throw/try/catch, własny wyjątek
    static void validateEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new InvalidEmailException("Invalid email: " + email);
    }
    public static void main(String[] args) {
        try {
            validateEmail("not-an-email");
        } catch (InvalidEmailException e) {
            System.out.println("Błąd: " + e.getMessage());
        } finally {
            System.out.println("finally: sprzątanie zasobów");
        }
    }
}

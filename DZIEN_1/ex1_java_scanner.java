import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println(">> Application started");
        System.out.println("Jak masz na imię? ");
        try (Scanner sc = new Scanner(System.in)) {
            String name = sc.nextLine();
            System.out.println("Witaj, " + name + "!");
        }
    }
}

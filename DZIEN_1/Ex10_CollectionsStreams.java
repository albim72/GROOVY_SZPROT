import java.util.*;
import java.util.stream.*;

public class Ex10_CollectionsStreams {
    // Cel: kolekcje, generics, strumienie, filtrowanie, mapowanie, reduce
    record User(String name, int age) {}
    public static void main(String[] args) {
        List<User> users = List.of(
            new User("Anna", 25), new User("Jan", 42),
            new User("Ola", 19), new User("Piotr", 33)
        );
        // Imiona dorosłych > 30, posortowane alfabetycznie
        List<String> names = users.stream()
            .filter(u -> u.age() > 30)
            .map(User::name)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Dorośli 30+: " + names);

        // Średni wiek
        double avg = users.stream().mapToInt(User::age).average().orElse(0);
        System.out.println("Avg age: " + avg);
    }
}

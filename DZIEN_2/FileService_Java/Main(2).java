import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TxtFileService service = new TxtFileService();
        Path path = Path.of("sample.txt");

        try {
            List<String> lines = Arrays.asList(
                    "pierwsza linia: dsfbksldkc",
                    "druga linia: dsfbksldkc",
                    "trzecia linia: dsfbksldkc"
            );
            service.writeLines(path,lines);

            //dopisanie linii
            service.appendLine(path,"dopisane na końcu!");

            //odczyt
            List<String> read = service.readLines(path);
            System.out.println("Zawartość pliku....");
            read.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

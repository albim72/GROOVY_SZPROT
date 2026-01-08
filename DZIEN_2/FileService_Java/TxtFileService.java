import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TxtFileService {
    public void writeLines(Path path, List<String> lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    public void appendLine(Path path, String line) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, 
                StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.APPEND, 
                java.nio.file.StandardOpenOption.CREATE)) {
            writer.write(line);
            writer.newLine();
        }
    }
    
    public List<String> readLines(Path path) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }
    
}

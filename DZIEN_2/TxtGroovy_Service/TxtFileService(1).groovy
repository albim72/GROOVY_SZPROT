package com.example.txt

import java.nio.file.*

class TxtFileService {

    /**
     * Zapisuje listę linii do pliku (nadpisuje istniejący plik).
     */
    void writeLines(Path path, List<String> lines) {
        Files.write(
                path,
                lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )
    }

    /**
     * Dopisuje jedną linię do pliku.
     */
    void appendLine(Path path, String line) {
        Files.write(
                path,
                [line],
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )
    }

    /**
     * Odczytuje wszystkie linie jako List<String>.
     */
    List<String> readLines(Path path) {
        if (!Files.exists(path)) {
            return []
        }
        return Files.readAllLines(path)
    }

    /**
     * Odczytuje cały tekst jako String.
     */
    String readAll(Path path) {
        if (!Files.exists(path)) {
            return ""
        }
        return Files.readString(path)
    }

    /**
     * Sprawdza, czy plik istnieje.
     */
    boolean exists(Path path) {
        return Files.exists(path)
    }
}

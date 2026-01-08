package com.example.txt

import java.nio.file.Path

class Main {
    static void main(String[] args) {

        def service = new TxtFileService()
        Path path = Path.of("sample.txt")

        println "--- ZAPIS ---"
        service.writeLines(path, [
                "Pierwsza linia",
                "Druga linia",
                "Trzecia linia"
        ])

        println "--- DOPISYWANIE ---"
        service.appendLine(path, "Dopisane na końcu")

        println "--- SPRAWDZANIE ---"
        println "Czy plik istnieje? " + service.exists(path)

        println "--- ODCZYT LINII ---"
        def lines = service.readLines(path)
        lines.each { println it }

        println "--- ODCZYT CAŁEGO TEKSTU ---"
        println service.readAll(path)
    }
}

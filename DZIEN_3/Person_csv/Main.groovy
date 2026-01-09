import java.nio.file.Path

class Main {
    static void main(String[] args) {
        def service = new CsvFileService()
        Path path = Path.of("persons.csv")

        def persons = [
                new Person(name: "Marcin", age: 53, city: "Lublin"),
                new Person(name: "Ewa", age: 43, city: "Warszawa"),
                new Person(name: "Tomasz", age: 29, city: "Kraków"),
                new Person(name: "Alina", age: 67, city: "Gdynia"),
                new Person(name: "Leon", age: 33, city: "Rzeszów")
        ]

        println("_____ zapis csv ____")
        service.writePersons(path,persons)

        println("_______ odczyt csv _______")
        def readBack = service.readPersons(path)
        readBack.each{println(it)}
    }
}

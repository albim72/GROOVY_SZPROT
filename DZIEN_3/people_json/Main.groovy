import java.nio.file.Path

class Main{
    static void main(String[] args) {
        def service = new JsonService()
        Path path = Path.of("people.json")

        println("_______ odczyt json ________")
        def people = service.readPeople(path)
        println(people)

        println("________ filtr json ________")
        def filtered = service.filterByAge(people,40)
        filtered.each {println("${it.name} (${it.age})")}

        println("_______ dodanie nowej osoby _______")
        service.addPerson(people,[
                name: "Tomasz",
                age: 66,
                city: "Gdańsk",
                hobbies: ["Chess","Travels"]

        ])

        println("______ zapis do pliku ______")
        Path npath = Path.of("npeople.json")
        service.writePeople(npath,people)
        println("zapisano do pliku")
    }
}

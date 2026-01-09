import javax.swing.JPasswordField
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class CsvFileService {
    /**
     * Service zapisuje list obiektów Person do
     * plku csv - z nagłówkiem
     */

    void writePersons(Path path, List<Person> persons){
        def lines = []
        //nagłówek
        lines << "name;age;city"
        persons.each {p->
            lines << "${p.name};${p.age};${p.city}"
        }
        Files.write(path,lines, StandardCharsets.UTF_8)
    }

    /**
     * odczyt pliku csv i mapowanie wierszy na obiekty Person
     */

    List<Person> readPersons(Path path){
        if (!Files.exists(path)){
            return []
        }

        def allLines = Files.readAllLines(path,StandardCharsets.UTF_8)
        if(allLines.isEmpty()){
            return []
        }

        //pomijamy nagłówek (pierwsza linia)
        def dataLines = allLines.tail()

        dataLines.collect{
            line ->
                def parts = line.split(';')
                if (parts.size() != 3){
                    return null
                }
                new Person(
                        name:parts[0],
                        age:parts[1] as int,
                        city:parts[2]
                )
        }.findAll{it != null}
    }
}

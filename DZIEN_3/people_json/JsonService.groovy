import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.file.Files
import java.nio.file.Path

class JsonService {
    /**
     * wczytywanie JSON
     */

    List readPeople(Path path){
        def text = Files.readString(path)
        return new JsonSlurper().parseText(text)
    }
    /**
     * Zapis List/Map -> JSON
     */

    void writePeople(Path path, List people){
        def json = JsonOutput.prettyPrint(JsonOutput.toJson(people))
        Files.writeString(path,json)
    }

    /**
     * Filtr -> osoby starsze niż podana granica wieku
     */
    List filterByAge(List people, int age){
        people.findAll{it.age>age}
    }

    /**
     * dodawanie nowej osoby
     */
    
    List addPerson(List people, Map newPerson){
        people << newPerson
        return people
    }

}


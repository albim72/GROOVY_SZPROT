import groovy.xml.MarkupBuilder
import groovy.xml.XmlSlurper

class XmlServiceGroovy {
    //czytanie źródła xml
    List<Person> readPeople(File file){
        def xml = new XmlSlurper().parse(file)
        //xml.people.person -> lista węzłów  <person>
        xml.person.collect{
            node ->
                new Person(
                        name: node.name.text(),
                        age: node.age.text().toInteger(),
                        city: node.age.text()
                )
        }
    }

    //tworzenie źródła xml
    void writePeople(File file, List<Person> people){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.people {
            people.each {
                p->
                    person {
                        name(p.name)
                        age(p.age)
                        city(p.city)
                    }
            }
        }
        file.text = writer.toString()
    }
}

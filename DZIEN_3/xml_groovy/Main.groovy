class Main{
    static void main(String[] args) {
        def service = new XmlServiceGroovy()
        def inFile = new File("people.xml")
        def outFile = new File("people_grv.xml")
        
        println("_____ odczyt xml ____")
        def people = service.readPeople(inFile)
        people.each {println(it)}

        println("_____ zapis xml ____")
        service.writePeople(outFile, people)
        println("Zapisano do $outFile.absolutePath")

    }
}

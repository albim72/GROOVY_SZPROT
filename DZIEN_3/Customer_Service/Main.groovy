class Main {
    static void main(String[] args) {
        def service = new CustomerService()

        println("___________ dodawanie klientów _____________")
        service.addCustomer("Leon","leon@firma.pl")
        service.addCustomer("Anna","anna@firma.pl")
        service.addCustomer("Jan","jan@firma.pl")
        service.addCustomer("Wanda","wanda@firma.pl")

        println("___________ wyświetlanie klientow _____________")
        service.getAll().each {println(it)}

        println("___________ wyświetlanie aktywnych klientow _____________")
        service.getActive().each {println(it)}

        println("___________ szukanie klientow _____________")
        def c = service.findById(2L)
        println(c?:"Nie znaleziono")

        println("___________ dezaktywacja klientow _____________")
        def ok = service.deactivate(2L)
        println("czy się udało? $ok")

        println("___________ wyświetlanie aktywnych klientow _____________")
        service.getActive().each {println(it)}
    }
}

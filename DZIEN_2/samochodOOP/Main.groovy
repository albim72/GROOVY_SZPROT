class Main {
    static void main(String[] args) {

        Samochod s1 = new SamochodSpalinowy("Toyota", 2018, 1998)
        Samochod s2 = new SamochodElektryczny("Tesla", 2022, 480)

        List<Samochod> flota = [s1, s2]

        println "=== FLOTA ==="
        flota.each { Samochod s ->
            println s.info()
        }
    }
}

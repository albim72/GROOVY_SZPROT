class Main {
    static void main(String[] args) {

        Samochod s1 = new SamochodSpalinowy("Toyota", 2018, 1998,7.2G,6.8G)
        Samochod s2 = new SamochodElektryczny("Tesla", 2022, 480,18.5G,1.25G)
        Samochod s3 = new SamochodHybrydowy("Toyota Hybrid",2021,1798,4.8G,
                6.8G,14.0G,1.25G,0.35G)

        List<Samochod> flota = [s1, s2, s3]

        println "=== FLOTA ==="
        flota.each { Samochod s ->
            println s.info()
        }
        //sortowanie wg vmax - rosnąco
        flota.sort {a,b -> a.maksymalnaPredkosc() <=> b.maksymalnaPredkosc()}

        println("\n=== FLOTA (sortowanie wg vmax rosnąco) ===")
        flota.each {println(it.info())}

    }
}

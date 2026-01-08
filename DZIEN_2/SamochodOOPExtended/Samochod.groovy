/**
 * Abstrakcyjna klasa bazowa opisująca samochód.
 * Nie można tworzyć jej instancji.
 */
abstract class Samochod {

    String marka
    int rokProdukcji

    Samochod(String marka, int rokProdukcji) {
        this.marka = marka
        this.rokProdukcji = rokProdukcji
    }

    /**
     * Zwraca opis rodzaju silnika.
     * Każda klasa potomna MUSI to zaimplementować.
     */
    abstract String rodzajSilnika()

    /**
     * Zwraca maksymalną prędkość samochodu.
     * Implementacja zależna od typu pojazdu.
     */
    abstract int maksymalnaPredkosc()

    /**
     * Wspólna metoda dla wszystkich samochodów.
     * Korzysta z metod abstrakcyjnych (polimorfizm).
     */

    //Metoda abstrakcyjna: koszt przejazdu na 100km
    abstract BigDecimal koszPrzejazduNa100km()
    
    String info() {
        return "${marka} (${rokProdukcji}) | silnik: ${rodzajSilnika()} | vmax: ${maksymalnaPredkosc()} km/h " +
                "| koszt/100km: ${koszPrzejazduNa100km()} PLN"
    }
}

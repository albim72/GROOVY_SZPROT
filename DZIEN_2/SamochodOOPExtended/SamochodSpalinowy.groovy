class SamochodSpalinowy extends Samochod {

    int pojemnoscSilnika  // cm3
    BigDecimal spalanieNa100 // L/100km
    BigDecimal cenaPaliwaZaL // PLN/L

    SamochodSpalinowy(String marka, int rokProdukcji, int pojemnoscSilnika, 
    BigDecimal spalanieNa100, BigDecimal cenaPaliwaZaL) {
        super(marka, rokProdukcji)
        this.pojemnoscSilnika = pojemnoscSilnika
        this.spalanieNa100 = spalanieNa100
        this.cenaPaliwaZaL = cenaPaliwaZaL
    }

    @Override
    String rodzajSilnika() {
        "spalinowy ${pojemnoscSilnika} cm3"
    }

    @Override
    int maksymalnaPredkosc() {
        // przykładowa prosta logika zależna od pojemności
        if (pojemnoscSilnika >= 2500) return 240
        if (pojemnoscSilnika >= 1600) return 210
        return 180
    }

    @Override
    BigDecimal koszPrzejazduNa100km() {
        return (spalanieNa100*cenaPaliwaZaL).setScale(2,BigDecimal.ROUND_HALF_UP)
    }
}

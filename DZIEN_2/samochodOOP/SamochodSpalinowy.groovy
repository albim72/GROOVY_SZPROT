class SamochodSpalinowy extends Samochod {

    int pojemnoscSilnika  // cm3

    SamochodSpalinowy(String marka, int rokProdukcji, int pojemnoscSilnika) {
        super(marka, rokProdukcji)
        this.pojemnoscSilnika = pojemnoscSilnika
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
}

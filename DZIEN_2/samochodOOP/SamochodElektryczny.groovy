class SamochodElektryczny extends Samochod {

    int zasieg  // km

    SamochodElektryczny(String marka, int rokProdukcji, int zasieg) {
        super(marka, rokProdukcji)
        this.zasieg = zasieg
    }

    @Override
    String rodzajSilnika() {
        "elektryczny"
    }

    @Override
    int maksymalnaPredkosc() {
        // przykładowa prosta logika zależna od zasięgu
        if (zasieg >= 500) return 200
        if (zasieg >= 350) return 185
        return 170
    }
}

class SamochodElektryczny extends Samochod {

    int zasieg  // km
    BigDecimal zuzycieKWhNa100 //KWh/100
    BigDecimal cenaKWh // PLN/KWh

    SamochodElektryczny(String marka, int rokProdukcji, int zasieg,
    BigDecimal zuzycieKWhNa100, BigDecimal cenaKWh) {
        super(marka, rokProdukcji)
        this.zasieg = zasieg
        this.zuzycieKWhNa100 = zuzycieKWhNa100
        this.cenaKWh = cenaKWh
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

    @Override
    BigDecimal koszPrzejazduNa100km() {
        return (zuzycieKWhNa100*cenaKWh).setScale(2,BigDecimal.ROUND_HALF_UP)
    }
}

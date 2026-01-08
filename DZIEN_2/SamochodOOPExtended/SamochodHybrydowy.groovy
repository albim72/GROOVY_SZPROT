class SamochodHybrydowy extends Samochod {

    //prosty model hybyrydy: cześć dystansu na prąd, część na paliwo
    int pojemnoscSilnika  // cm3
    BigDecimal spalanieNa100 // L/100km
    BigDecimal cenaPaliwaZaL // PLN/L

    BigDecimal zuzycieKWhNa100 //KWh/100
    BigDecimal cenaKWh // PLN/KWh

    BigDecimal udzialElektryczny

    SamochodHybrydowy(String marka, int rokProdukcji, int pojemnoscSilnika,
                      BigDecimal spalanieNa100, BigDecimal cenaPaliwaZaL,
                      BigDecimal zuzycieKWhNa100, BigDecimal cenaKWh) {
        super(marka, rokProdukcji)
        this.pojemnoscSilnika = pojemnoscSilnika
        this.spalanieNa100 = spalanieNa100
        this.cenaPaliwaZaL = cenaPaliwaZaL
        this.zuzycieKWhNa100 = zuzycieKWhNa100
        this.cenaKWh = cenaKWh
    } //udział jazdy elektrycznej w zakresie 0..1

    
    @Override
    String rodzajSilnika() {
        return "hybrydowy (spalinowy ${pojemnoscSilnika} cm3 + elektryczny)"
    }

    @Override
    int maksymalnaPredkosc() {
        if (pojemnoscSilnika >= 2000) return 220
        return 200
    }

    @Override
    BigDecimal koszPrzejazduNa100km() {
        def e = (zuzycieKWhNa100*cenaKWh)
        def s = (spalanieNa100*cenaPaliwaZaL)
        def ue = udzialElektryczny
        def us = (BigDecimal.ONE - udzialElektryczny)

        (ue*e + us*s).setScale(2,BigDecimal.ROUND_HALF_UP)
    }
                
}

class Engine {
    int horsepower

    Engine(int horsepower) {
        this.horsepower = horsepower
    }
    String info() {
        "Silnik - moc: ${horsepower} KM"
    }
}

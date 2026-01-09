class Car {
    String model
    private Engine engine //silnik jest integralną częścią samochodu
    Car(String model, int horsepower) {
        this.model = model
        this.engine = new Engine(horsepower)
    }

    String info() {
        "Samochód: ${model}, ${engine.info()}"
    }
}

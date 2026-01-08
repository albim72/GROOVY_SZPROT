class Cicrle extends Shape{
    double radius

    Cicrle(String name, double radius) {
        super(name)
        this.radius = radius
    }

    @Override
    double area() {
        return Math.PI*radius*radius
    }
}

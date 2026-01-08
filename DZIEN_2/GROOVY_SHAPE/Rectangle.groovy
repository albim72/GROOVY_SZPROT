class Rectangle extends Shape {
    double width
    double height
    Rectangle(String name,double width, double height) {
        super(name)
        this.width = width
        this.height = height
    }

    @Override
    double area() {
        return width*height
    }
}

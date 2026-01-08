public class Rectangle extends Shape {
    double width;
    double height;
    public Rectangle(String name, double width, double height) {
        super(name);
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }
}

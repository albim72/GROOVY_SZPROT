public class Rectangle {
    double width;
    double height;
    String name;
    public Rectangle(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }
    double area() {
        return width * height;
    }
    String info() {
        return "Shape: " + name + ",area [cm2]: " + area();
    }
}

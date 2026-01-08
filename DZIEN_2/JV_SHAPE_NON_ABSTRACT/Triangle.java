public class Triangle {
    double width;
    double height;
    String name;
    public Triangle(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    double area() {
        return width * height/2;
    }
    String info() {
        return "Shape: " + name + ",area [cm2]: " + area();
    }
}

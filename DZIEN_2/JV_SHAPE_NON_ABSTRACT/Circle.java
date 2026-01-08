public class Circle {
    double radius;
    String name;
    public Circle(String name, double radius) {
        this.name = name;
        this.radius = radius;
    }
    double area() {
        return Math.PI * Math.pow(radius, 2);
    }
    String info() {
        return "Shape: " + name + ",area [cm2]: " + area();
    }
}

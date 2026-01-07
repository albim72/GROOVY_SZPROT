interface Shape {
    double area();
}

class Circle implements Shape {
    private final double r;
    Circle(double r) { this.r = r; }
    public double area() { return Math.PI * r * r; }
}

class Rectangle implements Shape {
    private final double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; }
    public double area() { return w * h; }
}

public class Ex08_Shapes {
    // Cel: interfejsy, klasy, polimorfizm
    public static void main(String[] args) {
        Shape c = new Circle(2.0);
        Shape r = new Rectangle(3.0, 4.0);
        System.out.printf("Circle area=%.2f%n", c.area());
        System.out.printf("Rect area=%.2f%n", r.area());
    }
}

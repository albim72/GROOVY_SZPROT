abstract class Shape {
    String name;

    public Shape(String name) {
        this.name = name;
    }

    abstract double area();

    String info() {
        return "Shape: " + name + ",area: " + area();
    }
}

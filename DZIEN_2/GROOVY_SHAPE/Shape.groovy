abstract class Shape {
    String name

    Shape(String name) {
        this.name = name
    }
    
    abstract double area()
    
    String info(){
        "SHAPE: $name, area: ${area()}"
    }
}


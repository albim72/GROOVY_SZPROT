class Animal {
    String name;
    int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String speak(){
        return "..."
    }

    String info(){
        return "$name, age: $age"
    }
}

class Dog extends Animal {

    Dog(String name, int age) {
        super(name, age)
    }

    @Override
    String speak() {
        return "Hau hau!"
    }
}

class Cat extends Animal {

    Cat(String name, int age) {
        super(name, age)
    }

    @Override
    String speak() {
        return "Miau miau!"
    }
}

def animals = [
        new Dog("Ludvik",5),
        new Dog("Remi",2),
        new Cat("Mimi",3),
        new Cat("Romek",9)
]

animals.each {a->
    println(a.info() + " -> " + a.speak())
}

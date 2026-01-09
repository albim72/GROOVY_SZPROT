class Person {
    String name
    int age
    String city

    @Override
    String toString() {
        "$name ($age), $city"
    }
}

//GroovyBean -> nie trzeba budować pól get/set

class Person {
    String name
    int age
    String city

    @Override
    String toString() {
        "$name ($age), $city"
    }
}

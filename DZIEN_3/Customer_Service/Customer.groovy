class Customer {
    Long id
    String name
    String email
    boolean active = true

    @Override
    String toString() {
        "Customer(id=$id, name=$name, email=$email, active=$active)"
    }
}

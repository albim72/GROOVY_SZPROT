class Employee {
    String name
    String role

    Employee(String name, String role) {
        this.name = name
        this.role = role
    }
    
    String info() {
        "${name} (${role})"
    }
}

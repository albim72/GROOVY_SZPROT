class Company {
    String name
    List<Employee> employees = []

    Company(String name) {
        this.name = name
    }

    void addEmployee(Employee e){
        employees << e
    }

    String info() {
        def empList = employees.collect{it.info()}.join(", ")
        "Firma: ${name}, Pracownicy: [${empList}]"
    }
}

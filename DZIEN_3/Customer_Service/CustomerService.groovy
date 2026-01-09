import java.util.function.LongUnaryOperator

class CustomerService {
    //CustomerService -> trzyma stan, tworzy klientów, wyszukuje, filtruje, zmienia stan (aktywacja/dezaktywacja)
    private List<Customer> customers = []
    private Long nextId = 1L
     //dodaj nowego klienta
    Customer addCustomer(String name,String email){
        def c = new Customer(id: nextId++,name: name,email:email,active: true)
        customers << c
        return c
    }
    
    //zwraca listę wszyskich klientów
    List<Customer> getAll() {
        customers
    }
    
    //szuka klienta po ID
    Customer findById(Long id){
        customers.find{it.id == id}
    }
    
    //dezaktywacja klienta
    boolean deactivate(Long id){
        def c = findById(id)
        if (!c) return false
        c.active = false
        return false
    }
    
    //lista aktywnych klientów
    List<Customer> getActive() {
        customers.findAll{it.active}
    }
}

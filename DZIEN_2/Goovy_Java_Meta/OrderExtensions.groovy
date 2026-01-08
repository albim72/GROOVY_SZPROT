class OrderExtensions {
    static void enable() {
        Order.metaClass.withVat = {
            double rate ->
                delegate.amount *(1+rate)
        }
        
        Order.metaClass.summary = {
            "Order(${delegate.customer}, ${delegate.amount})"
        }
    }
}

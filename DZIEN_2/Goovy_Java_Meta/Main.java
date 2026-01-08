class Main{
    static void main(String[] args) {
        //włączenie rozszerzenia przed użyciem Order...
        OrderExtensions.enable()

        Order order = new Order(1560.0,"Marcin")
        println("Customer: ${order.customer}, amount: ${order.amount}")

        double totalWithVat = order.amount *1.23
        println("Cena z VAT: ${totalWithVat}")

        println(order.summary())
        println("With VAT(23%): ${order.withVat(0.23)}")
    }
}

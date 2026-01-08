public class Order {
    private final double amount;
    private final String customer;
    public Order(double amount, String customer) {
        this.amount = amount;
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }
}

package ca.ulaval.glo2003.domain.customer;

public class CustomerFactory {
    public Customer build(String customerName, String customerEmail, String customerPhoneNumber) {
        return new Customer(customerName, customerEmail, customerPhoneNumber);
    }
}

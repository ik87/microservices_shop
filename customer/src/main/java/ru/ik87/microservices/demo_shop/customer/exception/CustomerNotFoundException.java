package ru.ik87.microservices.demo_shop.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Could not find customer " + id);
    }
}

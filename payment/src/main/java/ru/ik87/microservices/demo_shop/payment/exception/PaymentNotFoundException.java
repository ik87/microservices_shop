package ru.ik87.microservices.demo_shop.payment.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String id) {
        super("Could not find payment " + id);
    }
}

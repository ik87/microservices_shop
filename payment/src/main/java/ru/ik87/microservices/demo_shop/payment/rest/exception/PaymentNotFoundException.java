package ru.ik87.microservices.demo_shop.payment.rest.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long id) {
        super("Could not find payment with order id " + id);
    }
}

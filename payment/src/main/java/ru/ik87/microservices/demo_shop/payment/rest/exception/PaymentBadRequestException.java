package ru.ik87.microservices.demo_shop.payment.rest.exception;

public class PaymentBadRequestException extends RuntimeException {
    public PaymentBadRequestException(Long id) {
        super("order has been paid. Order id " + id);
    }
}

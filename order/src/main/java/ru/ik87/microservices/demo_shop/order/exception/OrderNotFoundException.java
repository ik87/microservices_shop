package ru.ik87.microservices.demo_shop.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public  OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}

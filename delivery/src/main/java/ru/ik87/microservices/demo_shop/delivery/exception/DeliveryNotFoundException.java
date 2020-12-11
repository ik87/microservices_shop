package ru.ik87.microservices.demo_shop.delivery.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(Long id) {
        super("Could not find delivery where order id: " + id);
    }
}

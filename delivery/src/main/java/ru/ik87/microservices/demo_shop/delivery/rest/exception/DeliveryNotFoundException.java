package ru.ik87.microservices.demo_shop.delivery.rest.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(Long id) {
        super("Could not find delivery where order id: " + id);
    }
}

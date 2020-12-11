package ru.ik87.microservices.demo_shop.delivery.exception;

public class StatusNotAcceptableException extends RuntimeException {
    public StatusNotAcceptableException(String status) {
        super("status not correct " + status);
    }
}

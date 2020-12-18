package ru.ik87.microservices.demo_shop.delivery.rest.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}

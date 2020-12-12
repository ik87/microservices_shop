package ru.ik87.microservices.demo_shop.order.exception;

public class OrderBadRequestException extends RuntimeException {
    public OrderBadRequestException(Long id) {
        super("order has been confirmed.Order id " + id);
    }
}

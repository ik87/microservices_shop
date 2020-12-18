package ru.ik87.microservices.demo_shop.order.rest.exception;

public class OrderIsExistException extends RuntimeException {
    public  OrderIsExistException(Long id) {
        super("Order is exist " + id);
    }

}

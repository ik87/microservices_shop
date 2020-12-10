package ru.ik87.microservices.demo_shop.orders.controller;

public class OrderIsExistException extends RuntimeException {
    OrderIsExistException(Long id) {
        super("Order is exist " + id);
    }

}

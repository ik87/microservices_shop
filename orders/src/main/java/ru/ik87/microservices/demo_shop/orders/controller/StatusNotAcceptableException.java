package ru.ik87.microservices.demo_shop.orders.controller;

public class StatusNotAcceptableException extends RuntimeException {
    StatusNotAcceptableException(String status) {
        super("status not correct " + status);
    }
}

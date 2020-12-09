package ru.ik87.microservices.demo_shop.orders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StatusNotAcceptableAdvice {

    @ResponseBody
    @ExceptionHandler(StatusNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String statusNotFoundHandler(StatusNotAcceptableException ex) {
        return ex.getMessage();
    }
}

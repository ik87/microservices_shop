package ru.ik87.microservices.demo_shop.payment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PaymentNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String paymentNotFoundHandler(PaymentNotFoundException ex) {
        return ex.getMessage();
    }

}

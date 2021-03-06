package ru.ik87.microservices.demo_shop.payment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PaymentBadRequestAdvice {

    @ResponseBody
    @ExceptionHandler({ru.ik87.microservices.demo_shop.payment.rest.exception.PaymentBadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String paymentBadRequestHandler(ru.ik87.microservices.demo_shop.payment.rest.exception.PaymentBadRequestException ex) {
        return ex.getMessage();
    }
}

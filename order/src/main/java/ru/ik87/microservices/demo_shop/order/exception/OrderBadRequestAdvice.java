package ru.ik87.microservices.demo_shop.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderBadRequestAdvice {

    @ResponseBody
    @ExceptionHandler({OrderBadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String orderBadRequestHandler(OrderBadRequestException ex) {
        return ex.getMessage();
    }
}

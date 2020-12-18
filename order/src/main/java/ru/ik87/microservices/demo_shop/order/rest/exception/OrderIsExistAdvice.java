package ru.ik87.microservices.demo_shop.order.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderIsExistAdvice {

    @ResponseBody
    @ExceptionHandler({OrderIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String statusNotFoundHandler(OrderIsExistException ex) {
        return ex.getMessage();
    }

}

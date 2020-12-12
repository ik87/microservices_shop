package ru.ik87.microservices.demo_shop.payment.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.payment.exception.PaymentNotFoundException;
import ru.ik87.microservices.demo_shop.payment.model.Payment;
import ru.ik87.microservices.demo_shop.payment.repository.PaymentRepository;


@RestController
public class PaymentController {
    private final PaymentRepository repository;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/payment/{order_id}/pay")
    public Payment newPayment(@PathVariable Long order_id, @RequestAttribute String client_id) {
        //get order price info, get order status
        //get delivery info, get delivery status
        //get customer info

       // payment.setClientId(Long.valueOf(client_id));
       // return repository.save(payment);
        return null;
    }

    @GetMapping("/payment/{order_id}")
    public Payment getPayment(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Payment payment = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(payment == null) {
            throw new PaymentNotFoundException(order_id);
        }
        return payment;
    }
}

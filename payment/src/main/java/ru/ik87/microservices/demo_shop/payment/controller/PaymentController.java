package ru.ik87.microservices.demo_shop.payment.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.payment.exception.PaymentNotFoundException;
import ru.ik87.microservices.demo_shop.payment.model.Payment;
import ru.ik87.microservices.demo_shop.payment.repository.PaymentRepository;

import java.util.Optional;

@RestController
public class PaymentController {
    private final PaymentRepository repository;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/payment/{order_id}/pay")
    public Payment newPayment(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        //get order price info
        //get delivery info
        //get customer info

        payment.setClientId(Long.valueOf(client_id));
        return repository.save(payment);
    }

    @GetMapping("/payment/{order_id}")
    public Payment getPayment(@RequestAttribute("client_id") String client_id) {
        Optional<Payment> payment = repository.findById(Long.valueOf(client_id));
        if(payment.isEmpty()) {
            throw new PaymentNotFoundException(client_id);
        }
        return payment.get();
    }
}

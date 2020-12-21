package ru.ik87.microservices.demo_shop.payment.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.payment.persistence.model.Payment;
import ru.ik87.microservices.demo_shop.payment.persistence.model.PaymentStatus;
import ru.ik87.microservices.demo_shop.payment.persistence.repository.PaymentRepository;
import ru.ik87.microservices.demo_shop.payment.rest.exception.PaymentBadRequestException;
import ru.ik87.microservices.demo_shop.payment.rest.exception.PaymentNotFoundException;
import ru.ik87.microservices.demo_shop.payment.service.PaymentService;


@RestController
public class PaymentController {
    private final PaymentRepository repository;

    @Value("${kakfa.producer.topic.payment}")
    private String paymentTopic;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/payment/{order_id}/pay")
    public Payment newPayment(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Payment payment = repository.searchByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(payment == null) {
            throw new PaymentNotFoundException(order_id);
        }
        if(payment.getStatus() == PaymentStatus.PAID) {
            throw new PaymentBadRequestException(order_id);
        }
        boolean result = paymentService.pay(payment);

        if(result) {
            payment.setStatus(PaymentStatus.PAID);
            repository.save(payment);
            kafkaTemplate.send(paymentTopic, String.valueOf(payment.getDelivery().getDeliveryId()));
        }

        return payment;
    }

    @GetMapping("/payment/{order_id}")
    public Payment getPayment(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Payment payment = repository.searchByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(payment == null) {
            throw new PaymentNotFoundException(order_id);
        }
        return payment;
    }
}

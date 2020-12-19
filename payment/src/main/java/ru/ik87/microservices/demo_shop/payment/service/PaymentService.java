package ru.ik87.microservices.demo_shop.payment.service;

import ru.ik87.microservices.demo_shop.payment.persistence.model.Payment;

public interface PaymentService {
    boolean pay(Payment payment);
}

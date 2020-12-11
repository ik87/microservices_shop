package ru.ik87.microservices.demo_shop.payment.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.payment.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Payment findByOrderIdAndClientId(Long orderId, Long clientId);
}

package ru.ik87.microservices.demo_shop.payment.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.payment.persistence.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    @Query("SELECT p FROM PAYMENTS p WHERE p.order.orderId = ?1 AND p.clientId = ?2")
    Payment searchByOrderIdAndClientId(Long orderId, Long clientId);
}

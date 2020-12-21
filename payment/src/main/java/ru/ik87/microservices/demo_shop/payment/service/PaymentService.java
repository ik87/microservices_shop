package ru.ik87.microservices.demo_shop.payment.service;

import ru.ik87.microservices.demo_shop.payment.persistence.model.Payment;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 20.12.2020
 */
public interface PaymentService {
    /**
     * some logic: Authorize bank card, debit, etc..
     * @param payment Payment model
     * @return if success then true
     */
    boolean pay(Payment payment);
}

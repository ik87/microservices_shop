package ru.ik87.microservices.demo_shop.payment.service;

import org.springframework.stereotype.Component;
import ru.ik87.microservices.demo_shop.payment.persistence.model.Payment;

@Component
public class PaymentServiceImp implements PaymentService {
    @Override
    public boolean pay(Payment payment) {
       String bankCard = payment.getCustomer().getBankCard();
       Double costOfDelivery = payment.getDelivery().getPrice();
       Double costOfOrder = payment.getOrder().getPrice();
       //to do
        return true;
    }
}

package ru.ik87.microservices.demo_shop.delivery.service;

import org.springframework.stereotype.Component;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.Delivery;

@Component
public class CalculateDeliveryPriceImp implements CalculateDeliveryPrice {
    @Override
    public double calc(Delivery delivery) {
        return delivery.getCustomer().getAddress().length();
    }
}

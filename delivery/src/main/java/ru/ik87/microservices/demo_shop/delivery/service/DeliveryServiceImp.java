package ru.ik87.microservices.demo_shop.delivery.service;

import org.springframework.stereotype.Component;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;

@Component
public class DeliveryServiceImp implements DeliveryService {
    @Override
    public double calcPrice(Delivery delivery) {
        return delivery.getCustomer().getAddress().length();
    }
}

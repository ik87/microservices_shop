package ru.ik87.microservices.demo_shop.delivery.service;

import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.Delivery;

public interface CalculateDeliveryPrice {
    double calc(Delivery delivery);
}

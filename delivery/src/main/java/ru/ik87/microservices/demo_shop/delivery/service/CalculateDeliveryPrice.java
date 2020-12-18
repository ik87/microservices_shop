package ru.ik87.microservices.demo_shop.delivery.service;

import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;

public interface CalculateDeliveryPrice {
    public double calc(Delivery delivery);
}

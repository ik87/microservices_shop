package ru.ik87.microservices.demo_shop.order.kafka.service;

import org.springframework.stereotype.Service;
import ru.ik87.microservices.demo_shop.order.persistence.model.Order;

public interface KafkaSender {
    void send(Order order);
}

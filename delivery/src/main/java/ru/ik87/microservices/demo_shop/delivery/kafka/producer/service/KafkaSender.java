package ru.ik87.microservices.demo_shop.delivery.kafka.producer.service;

import org.springframework.stereotype.Service;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;

@Service
public interface KafkaSender {
    void send(Delivery delivery);
}

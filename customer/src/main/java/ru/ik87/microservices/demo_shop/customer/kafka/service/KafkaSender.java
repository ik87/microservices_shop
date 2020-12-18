package ru.ik87.microservices.demo_shop.customer.kafka.service;

import org.springframework.stereotype.Service;
import ru.ik87.microservices.demo_shop.customer.persistence.model.Customer;
@Service
public interface KafkaSender {
    void send(Customer customer);
}

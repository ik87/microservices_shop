package ru.ik87.microservices.demo_shop.delivery.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.delivery.rest.exception.DeliveryNotFoundException;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;

@RestController
public class DeliveryController {
    private final DeliveryRepository repository;
    @Autowired
    public DeliveryController(DeliveryRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/deliveries/{order_id}")
    Delivery getDelivery(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Delivery delivery = repository.searchByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(delivery == null) {
            throw new DeliveryNotFoundException(order_id);
        }
        return delivery;
    }
}

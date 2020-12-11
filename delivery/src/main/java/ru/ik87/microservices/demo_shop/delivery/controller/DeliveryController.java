package ru.ik87.microservices.demo_shop.delivery.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.delivery.exception.DeliveryNotFoundException;
import ru.ik87.microservices.demo_shop.delivery.exception.StatusNotAcceptableException;
import ru.ik87.microservices.demo_shop.delivery.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.model.DeliveryStatus;
import ru.ik87.microservices.demo_shop.delivery.repositroy.DeliveryRepository;

import java.util.Arrays;

@RestController
public class DeliveryController {
    private final DeliveryRepository repository;

    public DeliveryController(DeliveryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/deliveries")
    Delivery newDelivery(@RequestBody Delivery delivery, @RequestAttribute("client_id") String client_id) {
        delivery.setClientId(Long.valueOf(client_id));
        return repository.save(delivery);
    }

    @PostMapping("/deliveries/{order_id}/status")
    Delivery sendDelivery(@PathVariable Long order_id, @RequestBody String status, @RequestAttribute("client_id") String client_id) {
        String statusFormatted = status.replace("\"", "").toUpperCase();
        Delivery delivery = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        boolean statusExist = Arrays
                .stream(DeliveryStatus.values())
                .anyMatch(v -> v.name().equals(statusFormatted));
        if(delivery == null) {
            throw new DeliveryNotFoundException(order_id);
        }
        if (!statusExist) {
            throw new StatusNotAcceptableException(statusFormatted);
        }
        //get order and send on to address
        delivery.setStatus(DeliveryStatus.valueOf(statusFormatted));
        return repository.save(delivery);
    }

    @GetMapping("/deliveries/{order_id}")
    Delivery getDelivery(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        Delivery delivery = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(delivery == null) {
            throw new DeliveryNotFoundException(order_id);
        }
        return delivery;
    }
}

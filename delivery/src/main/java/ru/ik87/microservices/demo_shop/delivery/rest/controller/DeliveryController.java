package ru.ik87.microservices.demo_shop.delivery.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.delivery.kafka.producer.service.KafkaSender;
import ru.ik87.microservices.demo_shop.delivery.rest.exception.DeliveryNotFoundException;
import ru.ik87.microservices.demo_shop.delivery.rest.exception.BadRequestException;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;

@RestController
public class DeliveryController {
    private final DeliveryRepository repository;
    @Autowired
    private KafkaSender kafkaSender;
    public DeliveryController(DeliveryRepository repository) {
        this.repository = repository;
    }

//    @PostMapping("/deliveries")
//    Delivery newDelivery(@RequestBody Delivery newDelivery, @RequestAttribute String client_id) {
//        Delivery delivery = repository.findByOrderIdAndClientId(newDelivery.getOrderId(), Long.valueOf(client_id));
//        if(delivery != null) {
//            throw new BadRequestException("the parcel has already been created.Order id " + delivery.getOrderId());
//        }
//        newDelivery.setClientId(Long.valueOf(client_id));
//
//        return repository.save(newDelivery);
//    }

    @PostMapping("/deliveries/{order_id}/send")
    Delivery sendDelivery(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Delivery delivery = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(delivery == null) {
            throw new DeliveryNotFoundException(order_id);
        }
        if (delivery.getStatus() == DeliveryStatus.SEND) {
            throw new BadRequestException("the parcel has already been sent.Order id " + order_id);
        }
        delivery.setStatus(DeliveryStatus.SEND);
        return repository.save(delivery);
    }

    @GetMapping("/deliveries/{order_id}")
    Delivery getDelivery(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Delivery delivery = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if(delivery == null) {
            throw new DeliveryNotFoundException(order_id);
        }
        return delivery;
    }
}

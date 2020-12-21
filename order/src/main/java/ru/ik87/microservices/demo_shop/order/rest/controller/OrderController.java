package ru.ik87.microservices.demo_shop.order.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.order.kafka.service.KafkaSender;
import ru.ik87.microservices.demo_shop.order.rest.exception.OrderIsExistException;
import ru.ik87.microservices.demo_shop.order.rest.exception.OrderNotFoundException;
import ru.ik87.microservices.demo_shop.order.rest.exception.OrderBadRequestException;
import ru.ik87.microservices.demo_shop.order.persistence.model.Order;
import ru.ik87.microservices.demo_shop.order.persistence.model.OrderStatus;
import ru.ik87.microservices.demo_shop.order.persistence.repostitory.OrderRepository;


@RestController
public class OrderController {
    private final OrderRepository repository;
    @Autowired
    private KafkaSender kafkaSender;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders/{order_id}")
    Order findOrder(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        return order;
    }

    @PostMapping("/orders")
    Order newOrder(@RequestBody Order order, @RequestAttribute String client_id) {
        order.setClientId(Long.valueOf(client_id));
        if (repository.existsById(order.getOrderId())) {
            throw new OrderIsExistException(order.getOrderId());
        }
        order.setStatus(OrderStatus.NEW);
        return repository.save(order);
    }
    //change only price
    @PutMapping("/orders/{order_id}")
    Order replaceOrder(@PathVariable Long order_id, @RequestBody Order newOrder, @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new OrderBadRequestException(order_id);
        }
        order.setStatus(OrderStatus.NEW);
        order.setPrice(newOrder.getPrice());
        return repository.save(order);
    }
    //confirmed and send via kafka
    @PostMapping("/orders/{order_id}/confirmed")
    Order changeStatusOrder(@PathVariable Long order_id,  @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }

//        if (order.getStatus() == OrderStatus.CONFIRMED) {
//            throw new OrderBadRequestException(order_id);
//        }

        order.setStatus(OrderStatus.CONFIRMED);
        kafkaSender.send(order);
        return repository.save(order);
    }

    @DeleteMapping("/orders/{order_id}")
    void deleteOrder(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new OrderBadRequestException(order_id);
        }
        repository.delete(order);
    }
}

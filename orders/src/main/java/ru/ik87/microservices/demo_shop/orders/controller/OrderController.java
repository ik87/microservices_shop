package ru.ik87.microservices.demo_shop.orders.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.orders.model.Order;
import ru.ik87.microservices.demo_shop.orders.model.OrderStatus;
import ru.ik87.microservices.demo_shop.orders.repostitory.OrderRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders/{order_id}")
    Order findOrder(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        return repository.findByClientIdAndOrderId(Long.valueOf(client_id), order_id);
    }


    @PostMapping("/orders")
    Order newOrder(@RequestBody Order order, @RequestAttribute("client_id") String client_id) {
        order.setClientId(Long.valueOf(client_id));
        return repository.save(order);
    }

    @PutMapping("/orders/{order_id}")
    Order replaceOrder(@PathVariable Long order_id, @RequestBody Order newOrder, @RequestAttribute("client_id") String client_id) {
        Collection<OrderStatus> statuses = List.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
        Order order = repository.findByClientIdAndOrderIdAndStatusIn(Long.valueOf(client_id), order_id, statuses);
        if(!Objects.isNull(order)) {
            order.setPrice(newOrder.getPrice());
            return repository.save(order);
        }else {
            newOrder.setOrderId(order_id);
            newOrder.setClientId(Long.valueOf(client_id));
            newOrder.setStatus(OrderStatus.NEW);
            return repository.save(newOrder);
        }
    }

    @PutMapping("/orders/{order_id}/status")
    void changeStatusOrder(@PathVariable Long order_id, @RequestBody String status, @RequestAttribute("client_id") String client_id) {
        Order order = repository.findByClientIdAndOrderId(Long.valueOf(client_id), order_id);
       boolean statusExist = Arrays
                .stream(OrderStatus.values())
                .anyMatch(v->v.name().equals(status.toUpperCase()));

        if(!statusExist) {
            throw new StatusNotAcceptableException(status.toUpperCase());
        }

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));

    }

    @DeleteMapping("/orders/{order_id}")
    void deleteOrder(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        Collection<OrderStatus> statuses = List.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
        repository.deleteByClientIdAndOrderIdAndStatusIn(Long.valueOf(client_id), order_id, statuses);
    }
}

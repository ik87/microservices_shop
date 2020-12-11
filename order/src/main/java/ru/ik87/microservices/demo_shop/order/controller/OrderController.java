package ru.ik87.microservices.demo_shop.order.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.order.exception.OrderIsExistException;
import ru.ik87.microservices.demo_shop.order.exception.OrderNotFoundException;
import ru.ik87.microservices.demo_shop.order.exception.StatusNotAcceptableException;
import ru.ik87.microservices.demo_shop.order.model.Order;
import ru.ik87.microservices.demo_shop.order.model.OrderStatus;
import ru.ik87.microservices.demo_shop.order.repostitory.OrderRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class OrderController {
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders/{order_id}")
    Order findOrder(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        Order order = repository.findByClientIdAndOrderId(Long.valueOf(client_id), order_id);
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        return order;
    }


    @PostMapping("/orders")
    Order newOrder(@RequestBody Order order, @RequestAttribute("client_id") String client_id) {
        order.setClientId(Long.valueOf(client_id));
        if (repository.existsById(order.getOrderId())) {
            throw new OrderIsExistException(order.getOrderId());
        }
        return repository.save(order);
    }

    @PutMapping("/orders/{order_id}")
    Order replaceOrder(@PathVariable Long order_id, @RequestBody Order newOrder, @RequestAttribute("client_id") String client_id) {
        Collection<OrderStatus> statuses = List.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
        Order order = repository.findByClientIdAndOrderIdAndStatusIn(Long.valueOf(client_id), order_id, statuses);
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        newOrder.setClientId(Long.valueOf(client_id));
        order.setPrice(newOrder.getPrice());
        return repository.save(order);
    }

    @PutMapping("/orders/{order_id}/status")
    Order changeStatusOrder(@PathVariable Long order_id, @RequestBody String status, @RequestAttribute("client_id") String client_id) {
        String statusFormatted = status.replace("\"", "").toUpperCase();
        Order order = repository.findByClientIdAndOrderId(Long.valueOf(client_id), order_id);
        boolean statusExist = Arrays
                .stream(OrderStatus.values())
                .anyMatch(v -> v.name().equals(statusFormatted));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }

        if (!statusExist) {
            throw new StatusNotAcceptableException(statusFormatted);
        }

        order.setStatus(OrderStatus.valueOf(statusFormatted));
        return repository.save(order);
    }

    @DeleteMapping("/orders/{order_id}")
    void deleteOrder(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        Collection<OrderStatus> statuses = List.of(OrderStatus.NEW, OrderStatus.CONFIRMED);
        Order order = repository.findByClientIdAndOrderIdAndStatusIn(Long.valueOf(client_id), order_id, statuses);
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        repository.delete(order);
    }
}

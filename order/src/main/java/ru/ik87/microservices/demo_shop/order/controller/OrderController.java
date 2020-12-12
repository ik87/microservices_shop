package ru.ik87.microservices.demo_shop.order.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.order.exception.OrderIsExistException;
import ru.ik87.microservices.demo_shop.order.exception.OrderNotFoundException;
import ru.ik87.microservices.demo_shop.order.exception.OrderBadRequestException;
import ru.ik87.microservices.demo_shop.order.model.Order;
import ru.ik87.microservices.demo_shop.order.model.OrderStatus;
import ru.ik87.microservices.demo_shop.order.repostitory.OrderRepository;


@RestController
public class OrderController {
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders/{order_id}")
    Order findOrder(@PathVariable Long order_id, @RequestAttribute("client_id") String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
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
        order.setPrice(newOrder.getPrice());
        return repository.save(order);
    }

    @PostMapping("/orders/{order_id}/confirmed")
    Order changeStatusOrder(@PathVariable Long order_id, @RequestBody String status, @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new OrderBadRequestException(order_id);
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return repository.save(order);
    }

    @DeleteMapping("/orders/{order_id}")
    void deleteOrder(@PathVariable Long order_id, @RequestAttribute String client_id) {
        Order order = repository.findByOrderIdAndClientId(order_id, Long.valueOf(client_id));
        if (order == null) {
            throw new OrderNotFoundException(order_id);
        }
        repository.delete(order);
    }
}

package ru.ik87.microservices.demo_shop.orders.repostitory;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ik87.microservices.demo_shop.orders.model.Order;
import ru.ik87.microservices.demo_shop.orders.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByClientIdAndOrderIdAndStatusIn(Long clientId, Long orderId, Collection<OrderStatus> orderStatusCollection);
    Order findByClientIdAndOrderId(Long clientId, Long orderId);
}

package ru.ik87.microservices.demo_shop.order.repostitory;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.order.model.Order;
import ru.ik87.microservices.demo_shop.order.model.OrderStatus;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByClientIdAndOrderIdAndStatusIn(Long clientId, Long orderId, Collection<OrderStatus> orderStatusCollection);
    Order findByClientIdAndOrderId(Long clientId, Long orderId);
}

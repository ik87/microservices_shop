package ru.ik87.microservices.demo_shop.order.persistence.repostitory;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.order.persistence.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByOrderIdAndClientId(Long orderId, Long clientId);
}

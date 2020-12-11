package ru.ik87.microservices.demo_shop.delivery.repositroy;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.model.Delivery;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId);
}

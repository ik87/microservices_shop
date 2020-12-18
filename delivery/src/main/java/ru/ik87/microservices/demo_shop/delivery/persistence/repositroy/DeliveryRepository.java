package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Delivery findByOrderIdAndClientId(Long orderId, Long clientId);
    Delivery findByClientIdAndOrdersIsContaining(Long clientId, String order);
}
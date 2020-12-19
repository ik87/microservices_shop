package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.DeliveryStatus;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Delivery findByOrderIdAndClientId(Long orderId, Long clientId);
    Delivery findByOrderId(Long orderId);
    Delivery findByClientIdAndStatus(Long clientId, DeliveryStatus status);
}

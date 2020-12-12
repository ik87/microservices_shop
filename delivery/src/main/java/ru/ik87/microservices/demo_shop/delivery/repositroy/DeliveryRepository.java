package ru.ik87.microservices.demo_shop.delivery.repositroy;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.model.DeliveryStatus;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Delivery findByOrderIdAndClientId(Long orderId, Long clientId);
    Delivery findByOrderIdAndClientIdAndStatus(Long orderId, Long clientId, DeliveryStatus deliveryStatus);
}

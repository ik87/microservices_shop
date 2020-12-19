package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

   @Query("SELECT p FROM DELIVERIES p WHERE p.order.orderId = ?1 AND p.clientId = ?2")
   Delivery searchByOrderIdAndClientId(Long orderId, Long clientId);
}

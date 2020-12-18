package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeliveryRepositoryTest {



    @Autowired
    private DeliveryRepository repository;

    @Test
    public void findClientByOrder() {
        Delivery delivery = new Delivery();
        delivery.setClientId(234L);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setPrice(123D);
        Set<String> orderId = Set.of("222","333");
        repository.save(delivery);
        assertNotNull(delivery.getDeliveryId());
    }
}
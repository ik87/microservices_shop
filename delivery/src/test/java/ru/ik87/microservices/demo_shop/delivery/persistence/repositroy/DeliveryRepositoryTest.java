package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DeliveryRepositoryTest {


    @Autowired
    private DeliveryRepository repository;

//
//    @Test
//    public void findByOrderIdAndClientId() {
//        Delivery delivery = new Delivery();
//        delivery.setClientId(234L);
//        delivery.setStatus(DeliveryStatus.PENDING);
//        delivery.setPrice(123D);
//        delivery.setOrderId(222L);
//        Set<String> orderId = Set.of("222","333");
//       // testEntityManager.persist(repository);
//        repository.save(delivery);
//       Delivery result = repository.findByOrderIdAndClientId(222L, 234L);
//        assertNotNull(result);
//    }
//
//    @Test
//    public void findByClientIdAndStatusAndOrderIdIsNull() {
//        Delivery delivery = new Delivery();
//        delivery.setClientId(234L);
//        delivery.setStatus(DeliveryStatus.NEW);
//        delivery.setPrice(123D);
//        delivery.setOrderId(null);
//        Set<String> orderId = Set.of("222","333");
//        repository.save(delivery);
//
//        Delivery result = repository.findByClientIdAndStatus(234L, DeliveryStatus.NEW);
//        assertNotNull(result);
//    }
}
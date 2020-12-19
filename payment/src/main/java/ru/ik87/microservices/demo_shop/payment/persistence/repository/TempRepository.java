package ru.ik87.microservices.demo_shop.payment.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.payment.persistence.model.Temp;

public interface TempRepository extends CrudRepository<Temp, Long> {
}

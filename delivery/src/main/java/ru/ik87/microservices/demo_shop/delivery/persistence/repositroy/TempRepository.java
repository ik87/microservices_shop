package ru.ik87.microservices.demo_shop.delivery.persistence.repositroy;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.temporary.Temp;

public interface TempRepository extends CrudRepository<Temp, Long> {
}

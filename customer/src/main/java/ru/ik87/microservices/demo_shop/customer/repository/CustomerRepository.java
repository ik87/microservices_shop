package ru.ik87.microservices.demo_shop.customer.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ik87.microservices.demo_shop.customer.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

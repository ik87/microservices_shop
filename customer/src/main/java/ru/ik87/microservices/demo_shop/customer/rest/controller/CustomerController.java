package ru.ik87.microservices.demo_shop.customer.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.customer.kafka.service.KafkaSender;
import ru.ik87.microservices.demo_shop.customer.rest.exception.CustomerNotFoundException;
import ru.ik87.microservices.demo_shop.customer.persistence.model.Customer;
import ru.ik87.microservices.demo_shop.customer.persistence.repository.CustomerRepository;

import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository repository;
    @Autowired
    private KafkaSender kafkaSender;
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/customer")
    public Customer newCustomer(@RequestBody Customer customer,  @RequestAttribute String client_id) {
        customer.setClientId(Long.valueOf(client_id));
        kafkaSender.send(customer);
        return repository.save(customer);
    }

    @GetMapping("/customer")
    public Customer getCustomer(@RequestAttribute String client_id) {
        Optional<Customer> customer = repository.findById(Long.valueOf(client_id));
        if(customer.isEmpty()) {
            throw new CustomerNotFoundException(client_id);
        }
        return customer.get();
    }
}

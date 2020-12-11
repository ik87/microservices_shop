package ru.ik87.microservices.demo_shop.customers.controller;

import org.springframework.web.bind.annotation.*;
import ru.ik87.microservices.demo_shop.customers.exception.CustomerNotFoundException;
import ru.ik87.microservices.demo_shop.customers.model.Customer;
import ru.ik87.microservices.demo_shop.customers.repository.CustomerRepository;

import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/customer")
    public Customer newCustomer(@RequestBody Customer customer,  @RequestAttribute("client_id") String client_id) {
        customer.setClientId(Long.valueOf(client_id));
        return repository.save(customer);
    }

    @GetMapping("/customer")
    public Customer getCustomer(@RequestAttribute("client_id") String client_id) {
        Optional<Customer> customer = repository.findById(Long.valueOf(client_id));
        if(customer.isEmpty()) {
            throw new CustomerNotFoundException(client_id);
        }
        return customer.get();
    }
}

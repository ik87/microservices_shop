package ru.ik87.microservices.demo_shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OrdersApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OrdersApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}

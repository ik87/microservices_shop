package ru.ik87.microservices.demo_shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.orders.services.JwtService;
import ru.ik87.microservices.demo_shop.orders.services.JwtServiceImp;

import java.util.function.Function;

@SpringBootApplication
public class OrdersApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OrdersApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImp(secret);
    }



}

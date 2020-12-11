package ru.ik87.microservices.demo_shop.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.customer.service.JwtService;
import ru.ik87.microservices.demo_shop.customer.service.JwtServiceImp;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImp(secret);
    }
}

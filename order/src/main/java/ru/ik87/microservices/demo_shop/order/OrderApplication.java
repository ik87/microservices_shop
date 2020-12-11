package ru.ik87.microservices.demo_shop.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.order.services.JwtService;
import ru.ik87.microservices.demo_shop.order.services.JwtServiceImp;

@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImp(secret);
    }

}

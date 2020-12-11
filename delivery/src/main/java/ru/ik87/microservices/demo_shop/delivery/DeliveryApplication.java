package ru.ik87.microservices.demo_shop.delivery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.delivery.service.JwtService;
import ru.ik87.microservices.demo_shop.delivery.service.JwtServiceImp;

@SpringBootApplication
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImp(secret);
    }
}

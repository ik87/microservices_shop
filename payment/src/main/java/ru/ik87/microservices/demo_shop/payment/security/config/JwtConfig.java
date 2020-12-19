package ru.ik87.microservices.demo_shop.payment.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ik87.microservices.demo_shop.payment.security.service.JwtService;
import ru.ik87.microservices.demo_shop.payment.security.service.JwtServiceImp;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImp(secret);
    }
}

package ru.ik87.microservices.demo_shop.order.security.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String getClientIdFromJWT(String token);
    String generateJWT(String clientId);
}

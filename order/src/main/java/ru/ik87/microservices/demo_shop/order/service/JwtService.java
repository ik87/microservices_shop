package ru.ik87.microservices.demo_shop.order.service;

public interface JwtService {
    String getClientIdFromJWT(String token);
    String generateJWT(String clientId);
}

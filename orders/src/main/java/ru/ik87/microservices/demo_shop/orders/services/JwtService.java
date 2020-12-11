package ru.ik87.microservices.demo_shop.orders.services;

public interface JwtService {
    String getClientIdFromJWT(String token);
    String generateJWT(String clientId);
}

package ru.ik87.microservices.demo_shop.order.security.service;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 20.12.2020
 */
public interface JwtService {
    /**
     * Get Id client client by JWT token (claims - sub: clientid )
     * @param token jwt token
     * @return client id
     */
    String getClientIdFromJWT(String token);

    /**
     * Generate new JWT token with payload (claims - sub: clientid )
     * @param clientId
     * @return JWT
     */
    String generateJWT(String clientId);
}

package ru.ik87.microservices.demo_shop.orders.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static final String SECRET = "P6JfC0qi6HLcILeqC8CYeiyi1F7nsqaerBNs/UoB35Y=";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}

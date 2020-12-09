package ru.ik87.microservices.demo_shop.orders.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static ru.ik87.microservices.demo_shop.orders.security.SecurityConstants.SECRET;
import static ru.ik87.microservices.demo_shop.orders.security.SecurityConstants.TOKEN_PREFIX;

class JWTAuthorizationFilterTest {
    void generateJWT(String clientId, String privateKey) {
        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //System.out.println(Encoders.BASE64.encode(key.getEncoded()));
        byte[] secretBase64 = Decoders.BASE64.decode(privateKey);
        Key key = Keys.hmacShaKeyFor(secretBase64);
        System.out.println(Jwts.builder()
                .setSubject(clientId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact());

    }

    @Test
    public void whenCorrectJWTThenGetClientIdFirst() {
        String jwt = TOKEN_PREFIX + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNDI1MTczMDQyIn0.UKYRQGyNPCWf1-725hk747hL3Bxw0LoFxs89TE7MaBA";
        String expectedId = "2425173042";
        jwt = jwt.replace(TOKEN_PREFIX,"");
        String clientId = JWTAuthorizationFilter.getClientIdFromJWT(jwt);
        assertThat(clientId, is(expectedId));
    }

    @Test
    public void whenCorrectJWTThenGetClientIdSecond() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4NTc1MzU2MzU2In0.bWP92vguePHjcKlqsJmwwyvW6Mvuqs2ylFV0eEJT0Jw";
        String expectedId = "8575356356";
        String clientId = JWTAuthorizationFilter.getClientIdFromJWT(jwt);
        assertThat(clientId, is(expectedId));
    }

    public void generateJwtAndKey() throws Exception {
        String id = "8575356356";
        generateJWT(id, SECRET);
    }

}
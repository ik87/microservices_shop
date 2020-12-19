package ru.ik87.microservices.demo_shop.payment.security.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtServiceImp implements JwtService {
    private final String secret;

    public JwtServiceImp(String secret) {
        this.secret = secret;
    }

    @Override
    public String getClientIdFromJWT(String token) {
        String clientId = null;
        // parse the token.
        try {
            clientId = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {

        }
        return clientId;
    }


    @Override
    public String generateJWT(String clientId) {
        String token = null;
        byte[] secretBase64 = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(secretBase64);
        try {
            token = Jwts.builder()
                    .setSubject(clientId)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }  catch (JwtException e) {

        }
        return token;
    }
}

package ru.ik87.microservices.demo_shop.order.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ik87.microservices.demo_shop.order.services.JwtService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JWTAuthorizationFilterTest {
    @Value("${jwt.token_prefix}")
    private String tokenPrefix;
    @Value("${jwt.header_string}")
    private String headerPrefix;
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private JwtService jwtService;

    @Test
    public void whenCorrectJWTThenGetClientIdFirst() {
        String token = tokenPrefix + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNDI1MTczMDQyIn0.UKYRQGyNPCWf1-725hk747hL3Bxw0LoFxs89TE7MaBA";
        String expectedId = "2425173042";
        token = token.replace(tokenPrefix,"");
        String clientId = jwtService.getClientIdFromJWT(token);
        assertThat(clientId, is(expectedId));
    }

    @Test
    public void whenCorrectJWTThenGetClientIdSecond() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4NTc1MzU2MzU2In0.bWP92vguePHjcKlqsJmwwyvW6Mvuqs2ylFV0eEJT0Jw";
        String expectedId = "8575356356";
        String clientId = jwtService.getClientIdFromJWT(token);
        assertThat(clientId, is(expectedId));
    }

    @Test
    public void generateJwtAndKey() throws Exception {
        String id = "8575356356";
        String result = jwtService.generateJWT(id);
        assertNotNull(result);
    }

}
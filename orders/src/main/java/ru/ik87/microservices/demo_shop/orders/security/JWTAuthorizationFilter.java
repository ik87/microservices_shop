package ru.ik87.microservices.demo_shop.orders.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.ik87.microservices.demo_shop.orders.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HEADER_STRING);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            token = token.replace(TOKEN_PREFIX,"");
            String clientId = getClientIdFromJWT(token);
            if (clientId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                request.setAttribute("client_id", clientId);
                chain.doFilter(request,response);
            }
        }
    }

    static String getClientIdFromJWT(String token) {
        String clientId = null;
        // parse the token.
        try {
            clientId = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {

        }
        return clientId;
    }
}

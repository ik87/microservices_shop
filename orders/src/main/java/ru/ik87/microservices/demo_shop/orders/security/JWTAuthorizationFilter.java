package ru.ik87.microservices.demo_shop.orders.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.ik87.microservices.demo_shop.orders.security.SecurityConstants.*;

@Component
public class JWTAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletResponse response = (HttpServletResponse)servletResponse;
       HttpServletRequest request = (HttpServletRequest)servletRequest;

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
                filterChain.doFilter(request,response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

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

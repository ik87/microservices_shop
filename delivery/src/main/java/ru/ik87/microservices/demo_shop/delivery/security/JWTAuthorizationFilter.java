package ru.ik87.microservices.demo_shop.delivery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ik87.microservices.demo_shop.delivery.service.JwtService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthorizationFilter implements Filter {

    @Value("${jwt.token_prefix}")
    private String tokenPrefix;
    @Value("${jwt.header_string}")
    private String headerPrefix;
    @Autowired
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = request.getHeader(headerPrefix);
        if (token == null || !token.startsWith(tokenPrefix)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            token = token.replace(tokenPrefix, "");
            String clientId = jwtService.getClientIdFromJWT(token);
            if (clientId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                request.setAttribute("client_id", clientId);
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }


}

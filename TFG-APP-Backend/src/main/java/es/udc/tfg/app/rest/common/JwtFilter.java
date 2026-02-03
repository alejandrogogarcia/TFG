package es.udc.tfg.app.rest.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JwtFilter extends HttpFilter {

    private final JwtGenerator jwtGenerator;
    private final MessageSource messageSource;

    public JwtFilter(JwtGenerator jwtGenerator, MessageSource messageSource) {

        this.jwtGenerator = jwtGenerator;
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaderValue == null || !authHeaderValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String serviceToken = authHeaderValue.replace("Bearer ", "");
            JwtInfo jwtInfo = jwtGenerator.getInfo(serviceToken);
            request.setAttribute("serviceToken", serviceToken);
            request.setAttribute("userId", jwtInfo.getUserId());
            configureSecurityContext(jwtInfo.getDni(), jwtInfo.getRole());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String errorMessage = messageSource.getMessage(
                    "project.auth.error.invalidToken",
                    null,
                    LocaleContextHolder.getLocale()
            );
            response.getWriter().write("{\"globalError\":\"" + errorMessage + "\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void configureSecurityContext(String dni, String role) {

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(dni, null, authorities));

    }
}

package es.udc.tfg.app.rest.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JwtFilter extends HttpFilter {

    private JwtGenerator jwtGenerator;

    public JwtFilter(JwtGenerator jwtGenerator) {

        this.jwtGenerator = jwtGenerator;

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
            response.getWriter().write("{\"globalError\":\"Token inválido o expirado.\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void configureSecurityContext(String dni, String role) {

        Set<GrantedAuthority> authorities = new HashSet<>();
        System.out.println("Rol asignado al usuario en SecurityContext: ROLE_" + role);

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(dni, null, authorities));

    }
}

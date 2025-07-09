package es.udc.tfg.app.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        // Users
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/setPass").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}/resetPass").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/find").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/loginFromServiceToken").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/{id}/changePass").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/{id}/update").authenticated()



                        // Categories
                        .requestMatchers(HttpMethod.POST, "/category/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/category/{id}/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/category/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/category/find").authenticated()

                        // Products
                        .requestMatchers(HttpMethod.POST, "/product/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/{id}/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/{id}/stock").authenticated()
                        .requestMatchers(HttpMethod.GET, "/product/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/product/find").authenticated()

                        // Product Taxes
                        .requestMatchers(HttpMethod.POST, "/productTax/create").hasAnyRole("ADMIN","CLERK")
                        .requestMatchers(HttpMethod.PUT, "/productTax/{id}/update").hasAnyRole("ADMIN","CLERK")
                        .requestMatchers(HttpMethod.GET, "/productTax/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/productTax/all").authenticated()

                        // Clients
                        .requestMatchers(HttpMethod.POST, "/client/create").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/client/{id}/update").authenticated()
                        .requestMatchers(HttpMethod.GET, "/client/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/client/find").authenticated()

                        // Notes
                        .requestMatchers(HttpMethod.DELETE, "/note/{id}/remove").hasAnyRole("ADMIN","CLERK")
                        .requestMatchers(HttpMethod.POST, "/note/create").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/note/{id}/update").authenticated()
                        .requestMatchers(HttpMethod.GET, "/note/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/note/find").authenticated()

                        // Notelines
                        .requestMatchers(HttpMethod.POST, "/note/{id}/noteline/add").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/noteline/{id}/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/noteline/{id}/remove").authenticated()
                        .requestMatchers(HttpMethod.GET, "/noteline/{id}").authenticated()

                        // Invoices
                        .requestMatchers(HttpMethod.POST, "/invoice/create").hasAnyRole("ADMIN","CLERK")
                        .requestMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyRole("ADMIN","CLERK")
                        .requestMatchers(HttpMethod.GET, "/invoice/find").hasAnyRole("ADMIN","CLERK")

                        // Company Info
                        .requestMatchers(HttpMethod.GET, "/company").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/company").authenticated()

                        // Denegar cualquier otra petición
                        .anyRequest().denyAll());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config .setAllowedHeaders(Arrays.asList("*"));

        source.registerCorsConfiguration("/**", config);

        return source;

    }

}

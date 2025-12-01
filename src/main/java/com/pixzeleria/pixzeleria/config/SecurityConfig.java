package com.pixzeleria.pixzeleria.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Zona pública
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Pa que me deje entrar al panel del H2
                .requestMatchers("/h2-console/**").permitAll() 

                // Roles
                .requestMatchers(HttpMethod.GET, "/api/v1/orders").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/pizzas/**", "/api/v1/ingredients/**").hasAnyAuthority("VENDEDOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/pizzas/**", "/api/v1/ingredients/**").hasAnyAuthority("VENDEDOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            
            // Esto es pa ver la consola H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
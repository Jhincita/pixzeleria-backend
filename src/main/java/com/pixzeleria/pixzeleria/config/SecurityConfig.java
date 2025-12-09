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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 1. Permitir saludos del navegador (OPTIONS)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 2. Rutas Públicas (Login, Registro, Ver Menú)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/clients/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
                
                // 3. Rutas Abiertas (Para que no te falle el Dashboard/Pedidos por ahora)
                .requestMatchers("/api/orders/**").permitAll() 
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/employees/**").permitAll() // Agregué employees por si acaso

                // 4. Admin y Vendedor
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/pizzas/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 🔥 CORS PERMISIVO: Aceptamos a todos para evitar problemas en el deploy
        config.setAllowedOriginPatterns(List.of("*")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

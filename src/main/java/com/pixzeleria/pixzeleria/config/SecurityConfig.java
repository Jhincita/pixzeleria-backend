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
            .csrf(csrf -> csrf.disable()) 
            // Activamos CORS usando la configuración de abajo
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            
            .authorizeHttpRequests(auth -> auth
                // 1. IMPORTANTE: Permitir el "Preflight" (OPTIONS) para todo
                // Sin esto, React falla al intentar conectar aunque tengas permiso.
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // 2. Rutas Públicas (Login, Registro, Swagger, Consola H2)
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                
                // 3. Menú Público (Cualquiera puede ver qué vendemos)
                .requestMatchers(HttpMethod.GET, "/api/v1/ingredients").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/pizzas").permitAll()
                
                // Usuarios: Solo el ADMIN puede ver la lista, crear (vía panel), editar o borrar.
                .requestMatchers("/api/v1/users/**").hasAuthority("ADMIN")

                // Pedidos: Admin y Vendedor pueden ver la lista.
                .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAnyAuthority("VENDEDOR", "ADMIN")
                
                // Gestión de Menú: Crear/Editar Pizzas e Ingredientes
                .requestMatchers(HttpMethod.POST, "/api/v1/pizzas/**", "/api/v1/ingredients/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/pizzas/**", "/api/v1/ingredients/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                
                // Borrar cosas del menú u órdenes: SOLO ADMIN
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAuthority("ADMIN")
                
                // Crear orden (POST /orders) requiere estar logueado como mínimo
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
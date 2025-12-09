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

import java.util.Arrays;
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
                // 1. Permitir OPTIONS (preflight CORS)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 2. Rutas Públicas (Login, Registro)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/clients/register").permitAll()
                
                // 3. Ver menú (público)
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/ingredients/**").permitAll()
                
                // 4. Crear pedidos (público - cualquiera puede ordenar)
                .requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
                
                // 5. Ver/Gestionar pedidos (SOLO ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAuthority("ADMIN")
                
                // 6. Gestión de usuarios (SOLO ADMIN)
                .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/clients/**").hasAuthority("ADMIN")
                
                // 7. Gestión de empleados (SOLO ADMIN)
                .requestMatchers("/api/employees/**").hasAuthority("ADMIN")

                // 8. Gestión de pizzas (ADMIN y VENDEDOR)
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/pizzas/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/pizzas/**").hasAuthority("ADMIN")

                // 9. Todo lo demás requiere autenticación
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
        
        config.setAllowedOriginPatterns(Arrays.asList(
            "https://pixzeleria-full-production.up.railway.app",
            "http://localhost:*",
            "http://127.0.0.1:*"
        ));
        
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // Cache preflight por 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

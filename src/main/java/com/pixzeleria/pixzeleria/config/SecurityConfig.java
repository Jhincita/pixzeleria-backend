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
                        // 1. IMPORTANTE: Permitir el "saludo" inicial del navegador (OPTIONS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        // 2. Rutas Públicas
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/clients/**").permitAll()
                        // Nota: Dejé GET pizzas público para que el menú se vea sin login
                        .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll() 
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // 3. Reglas Específicas
                        // Permitir ver órdenes a todos (o cámbialo a authenticated() si prefieres)
                        .requestMatchers(HttpMethod.GET, "/api/orders").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll() // Para tu tabla de usuarios

                        // Rutas de Vendedor y Admin (Crear/Editar)
                        .requestMatchers(HttpMethod.POST, "/api/pizzas/**", "/api/ingredients/**").hasAnyAuthority("VENDEDOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/pizzas/**", "/api/ingredients/**").hasAnyAuthority("VENDEDOR", "ADMIN")
                        
                        // Rutas solo Admin (Borrar)
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")

                        // Todo lo demás requiere autenticación
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Usamos patrones (*) para que no te vuelva a molestar el CORS nunca más
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

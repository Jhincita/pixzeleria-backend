package com.pixzeleria.pixzeleria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Este archivo es netamente para que lo podamos conectar al frontend

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todas las rutas
                        .allowedOrigins("*") // Permite cualquier origen (Frontend, postman, celular, nintendoDS ndeaaaah)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos los verbos
                        .allowedHeaders("*"); // Permite todos los jeders
            }
        };
    }
}
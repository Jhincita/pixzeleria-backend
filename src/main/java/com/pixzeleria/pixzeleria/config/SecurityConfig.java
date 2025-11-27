//package com.pixzeleria.pixzeleria.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // *** ESTO SÍ DESACTIVA CSRF EN SS6 ***
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/register").permitAll()  // libre para todos
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//}


package com.pixzeleria.pixzeleria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())          // Disable CSRF for POST/PUT/DELETE testing
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()       // Allow everything for now
                );
        return http.build();
    }
}

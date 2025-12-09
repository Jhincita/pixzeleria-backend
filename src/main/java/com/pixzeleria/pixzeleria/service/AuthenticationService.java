package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.AuthenticationRequest;
import com.pixzeleria.pixzeleria.dto.AuthenticationResponse;
import com.pixzeleria.pixzeleria.dto.RegisterRequest;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        user.setRole(request.getRole() != null ? request.getRole() : Role.CLIENTE);

        user = repository.save(user);
        
        // 👇 AGREGAR ESTO:  Si es cliente, crear su perfil
        if (user.getRole() == Role.CLIENTE) {
            clientService.create(user.getId(), 0);
        }
        
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .build();
    }
}

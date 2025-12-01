package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.RegisterRequest; // Importante para recibir los datos
import com.pixzeleria.pixzeleria.model.User;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder; // Para encriptar la clave nueva

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ¡Puertas abiertas!
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inyección para las contraseñas

    // 1. LISTAR (GET)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. BORRAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 3. EDITAR (PUT) - ¡ESTE ES EL QUE TE FALTA! 🚨
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return userRepository.findById(id)
            .map(user -> {
                // Actualizamos los datos
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setUsername(request.getUsername());
                user.setRun(request.getRun());
                user.setStatus(request.getStatus());
                user.setRole(request.getRole());
                
                // Solo cambiamos la contraseña si viene una nueva (no vacía)
                if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                }
                
                return ResponseEntity.ok(userRepository.save(user));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
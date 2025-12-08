package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import com.pixzeleria.pixzeleria.dto.RegisterRequest; // Asegúrate de tener este DTO
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return userRepository.findById(id)
            .map(user -> {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setUsername(request.getUsername());
                user.setRun(request.getRun());
                user.setStatus(request.getStatus());
                user.setRole(request.getRole());
                
                if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                }
                
                return ResponseEntity.ok(userRepository.save(user));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
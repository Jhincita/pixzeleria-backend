package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.model.user.ClientProfile;
import com.pixzeleria.pixzeleria.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/{userId}/profile")
    public ResponseEntity<ClientProfile> addClientProfile(@PathVariable Long userId,
                                                          @RequestParam int loyaltyPoints) {
        return ResponseEntity.ok(clientService.create(userId, loyaltyPoints));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ClientProfile> getClientProfile(@PathVariable Long userId) {
        return ResponseEntity.of(clientService.getByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<ClientProfile>> getAllClients() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getUserByRole(@PathVariable String role) {
        return ResponseEntity.of(clientService.getByRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientProfile(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

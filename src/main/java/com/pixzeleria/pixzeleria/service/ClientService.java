package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.model.user.ClientProfile;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.ClientProfileRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientProfileRepository clientRepo;
    private final UserRepository userRepo;

    public ClientService(ClientProfileRepository clientRepo, UserRepository userRepo) {
        this.clientRepo = clientRepo;
        this.userRepo = userRepo;
    }

    // Create and attach to a user
    public ClientProfile create(Long userId, int loyaltyPoints) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ClientProfile profile = new ClientProfile();
        profile.setUser(user);
        profile.setLoyaltyPoints(loyaltyPoints);
        return clientRepo.save(profile);
    }

    // Get profile by user
    public Optional<ClientProfile> getByUser(Long userId) {
        return clientRepo.findByUserId(userId);
    }

    // List all clients
    public List<ClientProfile> getAll() {
        return clientRepo.findAll();
    }

    // find by role
    public Optional<User>getByRole(String role) {
        return userRepo.findByRole(Enum.valueOf(com.pixzeleria.pixzeleria.model.user.Role.class, role));
    }

    // Delete profile
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }
}

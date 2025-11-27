package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.ClientDTO;
import com.pixzeleria.pixzeleria.model.Client;
import com.pixzeleria.pixzeleria.repository.ClientRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(PasswordEncoder passwordEncoder, ClientRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }

    public Client register(ClientDTO dto) {
        Client  client = new Client();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        return clientRepository.save(client);
    }

    public @Nullable List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public @Nullable Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
}

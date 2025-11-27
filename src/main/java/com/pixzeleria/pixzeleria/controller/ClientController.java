package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.ClientDTO;
import com.pixzeleria.pixzeleria.model.Client;
import com.pixzeleria.pixzeleria.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    // create
    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO dto) {
        return ResponseEntity.ok(service.register(dto));
    }
    //read
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return service.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}

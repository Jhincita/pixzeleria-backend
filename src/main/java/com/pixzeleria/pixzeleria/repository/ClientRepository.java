package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String name);
    Optional<Client> findById(Long id);
}

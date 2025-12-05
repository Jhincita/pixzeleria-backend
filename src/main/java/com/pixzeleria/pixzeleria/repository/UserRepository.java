package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pixzeleria.pixzeleria.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);
}

package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}

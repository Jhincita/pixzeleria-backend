package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.order.OrderPizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPizzaRepository extends JpaRepository<OrderPizza, Long> {
}

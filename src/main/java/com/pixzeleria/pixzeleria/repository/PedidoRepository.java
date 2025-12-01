package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository <Order, Long> {

}

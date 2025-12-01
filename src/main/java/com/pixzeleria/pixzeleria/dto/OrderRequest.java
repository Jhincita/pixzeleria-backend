package com.pixzeleria.pixzeleria.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    // Lista simple de IDs de pizzas del menú (para pedidos rápidos)
    private List<Long> menuPizzaIds;
    
    // Aquí debería ir la lógica para las pizzas personalizadas, pero después aksks
    // Por ahora algo básico no más
}
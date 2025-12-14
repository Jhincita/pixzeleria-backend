package com.pixzeleria.pixzeleria.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    // Lista simple de IDs de pizzas del menú (para pedidos rápidos)
    private List<OrderPizzaDTO> pizzas;

    // sistema anterior : pedidos con pizzas del menú y personalizadas mezcladas
/*    @Deprecated
    private List<Long> menuPizzaIds;*/
}
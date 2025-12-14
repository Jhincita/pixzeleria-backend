package com.pixzeleria.pixzeleria.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderPizzaDTO {

    // Si es pizza del menú, solo necesitas el ID
    private Long menuPizzaId;

    // Si es pizza personalizada, necesitas estos campos
    private boolean custom;  // true = personalizada, false = del menú
    private String name;
    private String size;
    private List<Long> ingredientIds;
    private Double totalPrice;

    private int quantity;  // Cantidad de esta pizza
}
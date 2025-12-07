package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PizzaDTO {
    private Long id;
    private String name;
    private Integer price;
    private String size;
    private List<Long> ingredientIds; 

    // --- Campos antiguos (se mantienen para el "Arma tu Pizza" o respuestas) ---
    private Double basePrice;
    private List<IngredientDTO> ingredients;
    private int totalPrice;
    
    // Opcional: Stock (aunque las pizzas no suelen tener stock directo, el form lo envía)
    private Integer stock; 
}


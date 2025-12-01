package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PizzaDTO {
    private String name;
    private Double basePrice;
    private String size;
    private List<IngredientDTO> ingredients;
    private int totalPrice;
}


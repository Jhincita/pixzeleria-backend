package com.pixzeleria.pixzeleria.dto;

import com.pixzeleria.pixzeleria.model.Ingredient;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PizzaDTO {
    private String name;
    private List<Long> ingredientIds;
}


package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {
    private Long id;
    private String name;
    private int stock;

    private String imageUrl;
}

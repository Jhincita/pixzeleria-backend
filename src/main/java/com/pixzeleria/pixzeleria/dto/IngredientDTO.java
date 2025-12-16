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

    private String type;   // 'masa', 'salsa', or 'topping'
    private String color;  // For salsas (hex color like '#E53935
    private int price;     // Price in CLP (e.g., 500)
}

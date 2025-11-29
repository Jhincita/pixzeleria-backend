package com.pixzeleria.pixzeleria.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {

    private String name;
    private int stock;
}

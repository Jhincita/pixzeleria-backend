package com.pixzeleria.pixzeleria.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stock;
    private String imageUrl;

    // NEW FIELDS for pizza builder
    private String type;   // 'masa', 'salsa', or 'topping'
    private String color;  // For salsas (hex color like '#E53935')
    private int price;     // Price in CLP (e.g., 500)
}
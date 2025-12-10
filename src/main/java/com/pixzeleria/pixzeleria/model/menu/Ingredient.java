package com.pixzeleria.pixzeleria.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence. Entity;
import jakarta.persistence. GeneratedValue;
import jakarta. persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok. Setter;

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
}

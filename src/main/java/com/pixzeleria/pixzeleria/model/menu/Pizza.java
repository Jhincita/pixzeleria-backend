package com.pixzeleria.pixzeleria.model. menu;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pizza extends Product {

    private String size;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "pizza_ingredients",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    public String getSize() { 
        return size; 
    }
    
    public void setSize(String size) { 
        this.size = size; 
    }
    
    public List<Ingredient> getIngredients() { 
        return ingredients; 
    }
    
    public void setIngredients(List<Ingredient> ingredients) { 
        this.ingredients = ingredients; 
    }
}
package com.pixzeleria.pixzeleria.model. menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pizza extends Product {

    private String size;

    @ManyToMany(cascade = {CascadeType. PERSIST, CascadeType. MERGE}, fetch = FetchType.LAZY) 
    @JoinTable(
        name = "pizza_ingredients",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @JsonIgnoreProperties("pizzas")
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
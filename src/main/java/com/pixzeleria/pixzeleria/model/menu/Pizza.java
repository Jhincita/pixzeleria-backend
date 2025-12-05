package com.pixzeleria.pixzeleria.model.menu;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pizza")
public class Pizza extends Product {

    private String name;
    @ManyToMany
    @JoinTable(
            name = "pizza_ingredient",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
<<<<<<< Updated upstream
=======
    private String size;
    private int price;
    private String imageUrl;
    private String pixelImageUrl;
>>>>>>> Stashed changes
}

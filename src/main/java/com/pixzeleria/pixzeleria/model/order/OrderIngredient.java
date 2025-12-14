package com.pixzeleria.pixzeleria.model.order;

import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_ingredient")
public class OrderIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_pizza_id")
    private OrderPizza orderPizza;  // <-- not menu pizza

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;  // ingredient definition is still menu-level

    private int quantity;
    private boolean removed;  // customer took ingredient out
    private boolean added;    // customer added extra
}

package com.pixzeleria.pixzeleria.model.order;

import com.pixzeleria.pixzeleria.model.menu.Pizza;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class OrderPizza extends OrderItem {

    @ManyToOne(optional = false)
    private Pizza basePizza; // The menu pizza selected

    @OneToMany(mappedBy = "orderPizza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderIngredient> ingredients = new ArrayList<>();

    private Double finalPrice;
}

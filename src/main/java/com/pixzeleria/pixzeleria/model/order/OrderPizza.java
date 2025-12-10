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
@DiscriminatorValue("OrderPizza")
public class OrderPizza extends OrderItem {

    @ManyToOne(optional = true)
    private Pizza basePizza;

    @OneToMany(mappedBy = "orderPizza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderIngredient> ingredients = new ArrayList<>();

    private Double finalPrice;
}

package com.pixzeleria.pixzeleria.model.order;

import com.fasterxml.jackson. annotation.JsonIgnore;
import com.fasterxml.jackson. annotation.JsonIgnoreProperties;
import com.pixzeleria.pixzeleria.model.menu.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("OrderItem") 
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"ingredients", "category", "hibernateLazyInitializer", "handler"})
    private Product product;

    private int quantity;
    private double price;
}

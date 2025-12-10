package com.pixzeleria.pixzeleria.model. menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pixzeleria.pixzeleria.model. Category;
import jakarta.persistence.*;
import lombok. Getter;
import lombok. Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType. JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String imageUrl;
    private String name;
    
    @Column(nullable = false)
    private Integer price = 0;  
}
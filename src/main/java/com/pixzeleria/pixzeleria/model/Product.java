package com.pixzeleria.pixzeleria.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

}

package com.pixzeleria.pixzeleria.model;

import jakarta.persistence.Entity;

@Entity
public class Pizza extends Product {

    private String size;

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
}

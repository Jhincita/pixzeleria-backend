package com.pixzeleria.pixzeleria.model.menu;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Drink extends Product {

    private Integer milliliters;

}

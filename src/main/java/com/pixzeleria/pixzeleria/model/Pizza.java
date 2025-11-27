package com.pixzeleria.pixzeleria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Pizza extends Product{


    private String size;
    private List<Ingredient> ingredients;

}

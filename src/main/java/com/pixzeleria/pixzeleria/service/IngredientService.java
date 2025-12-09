package com.pixzeleria.pixzeleria.service;


import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;


    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    // crud

    // create
    public Ingredient save(Ingredient dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.getName());
        ingredient.setStock(dto.getStock());
        ingredient.setImageUrl(dto.getImageUrl());
        return repository.save(ingredient);
    }
    // read
    public List<Ingredient> findAll() {
        return repository.findAll();
    }
    public Ingredient findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    //
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}

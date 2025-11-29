package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.IngredientDTO;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    
    private final IngredientService service;
    
    // crud
    //create
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody IngredientDTO ingredient) {

        return ResponseEntity.ok(service.save(ingredient));
    }

    // read
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}

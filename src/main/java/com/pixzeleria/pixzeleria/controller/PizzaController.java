package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pizzas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody PizzaDTO pizza) {
        return ResponseEntity.ok(pizzaService.savePizza(pizza));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, @RequestBody PizzaDTO pizza) {
        return ResponseEntity.ok(pizzaService.updatePizza(id, pizza));
    }
}
package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @PostMapping("/custom")
    public ResponseEntity<Pizza> createCustomPizza(@RequestBody PizzaDTO dto) {
        Pizza pizza = pizzaService.createCustomPizza(dto);
        return ResponseEntity.ok(pizza);
    }

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
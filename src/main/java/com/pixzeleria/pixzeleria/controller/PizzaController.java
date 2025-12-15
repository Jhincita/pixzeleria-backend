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

    // ============ ENDPOINTS DEL MENÚ (Admin) ============

    @PostMapping
    public ResponseEntity<Pizza> createMenuPizza(@RequestBody PizzaDTO dto) {
        return ResponseEntity.ok(pizzaService.saveMenuPizza(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updateMenuPizza(@PathVariable Long id, @RequestBody PizzaDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(pizzaService.saveMenuPizza(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }

    // ============ ENDPOINTS PÚBLICOS (Ver menú) ============

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
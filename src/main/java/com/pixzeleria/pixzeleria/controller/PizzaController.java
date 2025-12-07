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

    @PostMapping
    public ResponseEntity<Pizza> createMenuPizza(@RequestBody PizzaDTO dto) {
        // Si te sale error aquí en 'saveMenuPizza', es que te falta actualizar PizzaService también
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

    // Endpoint público (Ver menú)
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

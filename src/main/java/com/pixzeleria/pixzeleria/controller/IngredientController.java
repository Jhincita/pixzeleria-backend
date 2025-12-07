package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.IngredientDTO;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {


    private final IngredientService service;

    // CREATE
    @PostMapping
    public ResponseEntity<IngredientDTO> create(@RequestBody IngredientDTO dto) {
        Ingredient saved = service.save(dtoToEntity(dto));
        return ResponseEntity.ok(entityToDTO(saved));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAll() {
        List<IngredientDTO> list = service.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getById(@PathVariable Long id) {
        Ingredient ingredient = service.findById(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityToDTO(ingredient));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> update(@PathVariable Long id, @RequestBody IngredientDTO dto) {
        Ingredient existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setName(dto.getName());
        existing.setStock(dto.getStock());
        Ingredient updated = service.save(existing);
        return ResponseEntity.ok(entityToDTO(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Helpers
    private IngredientDTO entityToDTO(Ingredient entity) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStock(entity.getStock());
        return dto;
    }

    private Ingredient dtoToEntity(IngredientDTO dto) {
        Ingredient entity = new Ingredient();
        entity.setName(dto.getName());
        entity.setStock(dto.getStock());
        return entity;
    }

}

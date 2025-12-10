package com.pixzeleria.pixzeleria.service;

import com. pixzeleria.pixzeleria. dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.menu. Ingredient;
import com.pixzeleria.pixzeleria.model. menu.Pizza;
import com. pixzeleria.pixzeleria. repository.IngredientRepository;
import com.pixzeleria. pixzeleria.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository; 

    public Pizza createCustomPizza(PizzaDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        if (dto.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("El precio no puede ser menor o igual a cero");
        }
        
        Pizza pizza = new Pizza();
        pizza.setName(dto.getName());
        pizza.setPrice(dto.getTotalPrice());
        pizza.setSize(dto.getSize() != null ? dto.getSize() : "Medium");

        return pizzaRepository.save(pizza);
    }

    public Pizza saveMenuPizza(PizzaDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        Pizza pizza = new Pizza();

        if (dto.getId() != null) {
            pizza = pizzaRepository.findById(dto. getId()).orElse(new Pizza());
        }

        pizza. setName(dto.getName());
        
        Integer finalPrice = dto.getPrice() != null ? dto.getPrice() : 
                            (dto.getTotalPrice() != 0 ? dto.getTotalPrice() : 0);
        
        if (finalPrice == null || finalPrice <= 0) {
            throw new IllegalArgumentException("El precio no puede ser nulo o menor o igual a cero");
        }
        
        pizza.setPrice(finalPrice);
        pizza.setSize("Medium");

        if (dto.getIngredientIds() != null && !dto.getIngredientIds().isEmpty()) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());
            pizza.setIngredients(ingredients);
        }

        return pizzaRepository.save(pizza);
    }

    public void deletePizza(Long id) {
        pizzaRepository. deleteById(id);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }
}

package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    public Pizza savePizza(PizzaDTO pizzaDTO) {
        Pizza pizza = new Pizza();
        pizza.setName(pizzaDTO.getName());
        
        pizza.setPrice(pizzaDTO.getPrice());
        pizza.setStock(pizzaDTO.getStock());

        List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDTO.getIngredientIds());
        pizza.setIngredients(ingredients);
        return pizzaRepository.save(pizza);
    }

    public Pizza updatePizza(Long id, PizzaDTO pizzaDTO) {
        return pizzaRepository.findById(id).map(pizza -> {
            pizza.setName(pizzaDTO.getName());
            
            // Actualizamos precio y stock
            pizza.setPrice(pizzaDTO.getPrice());
            pizza.setStock(pizzaDTO.getStock());
            
            List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDTO.getIngredientIds());
            pizza.setIngredients(ingredients);
            
            return pizzaRepository.save(pizza);
        }).orElseThrow(() -> new RuntimeException("Pizza no encontrada"));
    }

    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
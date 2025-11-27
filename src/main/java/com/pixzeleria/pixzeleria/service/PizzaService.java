package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.Ingredient;
import com.pixzeleria.pixzeleria.model.Pizza;
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

        // Convertimos la lista de IDs (números) en lista de Ingredientes (objetos)
        // Buscamos en la base de datos todos los ingredientes que coincidan con los IDs
        List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDTO.getIngredientIds());
        pizza.setIngredientList(ingredients);
        return pizzaRepository.save(pizza);
    }
    
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
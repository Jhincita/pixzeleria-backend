package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.PizzaDTO;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public Pizza createCustomPizza(PizzaDTO dto) {
        Pizza pizza = new Pizza();
        pizza.setName(dto.getName());
        pizza.setPrice(dto.getTotalPrice());
        pizza.setSize(dto.getSize() != null ? dto.getSize() : "Medium");

        return pizzaRepository.save(pizza);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }
}
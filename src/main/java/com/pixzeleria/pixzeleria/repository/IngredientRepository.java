package com.pixzeleria.pixzeleria.repository;

import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
    Ingredient findById(long id);

//    Ingredient findByIfStock(boolean ifStock); <<< este metodo debería ir en el service.
}

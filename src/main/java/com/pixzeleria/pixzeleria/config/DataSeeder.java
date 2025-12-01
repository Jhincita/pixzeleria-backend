package com.pixzeleria.pixzeleria.config;

import com.pixzeleria.pixzeleria.model.Category;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.CategoryRepository;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Seed categories if empty
        if (categoryRepository.count() == 0) {
            Category pizzas = new Category(); pizzas.setName("Pizzas");
            Category bebidas = new Category(); bebidas.setName("Bebidas");
            Category ingredientes = new Category(); ingredientes.setName("Ingredientes");

            categoryRepository.saveAll(Arrays.asList(pizzas, bebidas, ingredientes));
            System.out.println("✅ Categorías cargadas");
        }

        // Seed ingredients if empty
        if (ingredientRepository.count() == 0) {
            Ingredient i1 = new Ingredient(); i1.setName("Masa Tradicional"); i1.setStock(250);
            Ingredient i2 = new Ingredient(); i2.setName("Salsa de Tomate"); i2.setStock(250);
            Ingredient i3 = new Ingredient(); i3.setName("Queso Mozzarella"); i3.setStock(250);
            Ingredient i4 = new Ingredient(); i4.setName("Pepperoni"); i4.setStock(250);
            Ingredient i5 = new Ingredient(); i5.setName("Piña"); i5.setStock(250);

            ingredientRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5));
            System.out.println("✅ Ingredientes cargados");
        }

        // Seed admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123")); // default password
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("✅ Usuario Admin creado (User: admin / Pass: 123)");
        }
    }
}

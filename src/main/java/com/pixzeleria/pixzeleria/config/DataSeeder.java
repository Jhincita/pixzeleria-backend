package com.pixzeleria.pixzeleria.config;

import com.pixzeleria.pixzeleria.model.Category;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.repository.CategoryRepository;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;
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
        // DELETE IN ORDER: pizzas first, then ingredients



        ingredientRepository.deleteAll();
        System.out.println("🗑️ Ingredientes eliminados");
        // Seed ingredients if empty
        if (ingredientRepository.count() == 0) {
            Ingredient i1 = new Ingredient(); i1.setName("Masa Tradicional"); i1.setStock(250);
            Ingredient i2 = new Ingredient(); i2.setName("Salsa de Tomate"); i2.setStock(250);
            Ingredient i3 = new Ingredient(); i3.setName("Queso Mozzarella"); i3.setStock(250);
            Ingredient i4 = new Ingredient(); i4.setName("Pepperoni"); i4.setStock(250);
            Ingredient i5 = new Ingredient(); i5.setName("Piña"); i5.setStock(250);

            // Add more ingredients for the different pizzas
            Ingredient i6 = new Ingredient(); i6.setName("Prosciutto"); i6.setStock(250);
            Ingredient i7 = new Ingredient(); i7.setName("Pistacchio"); i7.setStock(250);
            Ingredient i8 = new Ingredient(); i8.setName("Rúgula"); i8.setStock(250);
            Ingredient i9 = new Ingredient(); i9.setName("Tomates Cherry"); i9.setStock(250);
            Ingredient i10 = new Ingredient(); i10.setName("Mozzarella di Bufala"); i10.setStock(250);
            Ingredient i11 = new Ingredient(); i11.setName("Albahaca"); i11.setStock(250);

            ingredientRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11));
            System.out.println("✅ Ingredientes cargados");
        }
        // Seed admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("jojo123")); // default password
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("✅ Usuario Admin creado (User: admin / Pass: 123)");
        }

        // Seed pizzas with ingredients if empty
        // Clear existing pizzas (for development only!)
        // pizzaRepository.deleteAll();
        System.out.println("🗑️ Pizzas existentes eliminadas");
        if (pizzaRepository.count() == 0) {
            // Fetch ingredients
            Ingredient masa = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Masa Tradicional"))
                    .findFirst().orElse(null);
            Ingredient salsa = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Salsa de Tomate"))
                    .findFirst().orElse(null);
            Ingredient queso = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Queso Mozzarella"))
                    .findFirst().orElse(null);
            Ingredient pepperoni = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Pepperoni"))
                    .findFirst().orElse(null);
            Ingredient prosciutto = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Prosciutto"))
                    .findFirst().orElse(null);
            Ingredient pistacchio = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Pistacchio"))
                    .findFirst().orElse(null);
            Ingredient rugula = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Rúgula"))
                    .findFirst().orElse(null);
            Ingredient tomatesCherry = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Tomates Cherry"))
                    .findFirst().orElse(null);
            Ingredient bufala = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Mozzarella di Bufala"))
                    .findFirst().orElse(null);
            Ingredient albahaca = ingredientRepository.findAll().stream()
                    .filter(i -> i.getName().equals("Albahaca"))
                    .findFirst().orElse(null);

            String baseUrl = "https://pub-3108682005f34a1e90099e4d00f82f95.r2.dev/menu_pizzas/";

            // Pizza Margherita (Masa + Salsa + Queso + Albahaca)
            Pizza p1 = new Pizza();
            p1.setName("Pizza Margherita");
            p1.setPrice(8000);
            p1.setSize("MEDIUM");
            p1.setIngredients(Arrays.asList(masa, salsa, queso, albahaca));
            p1.setImageUrl(baseUrl + "pizzamargherita.png");

            // Pizza Prosciutto Pistacchio (Masa + Salsa + Queso + Prosciutto + Pistacchio)
            Pizza p2 = new Pizza();
            p2.setName("Pizza Prosciutto Pistacchio");
            p2.setPrice(10000);
            p2.setSize("MEDIUM");
            p2.setIngredients(Arrays.asList(masa, salsa, queso, prosciutto, pistacchio));
            p2.setImageUrl(baseUrl + "prosciuttopistaccio.png");

            // Pizza Pepperoni (Masa + Salsa + Queso + Pepperoni)
            Pizza p3 = new Pizza();
            p3.setName("Pizza Pepperoni");
            p3.setPrice(9000);
            p3.setSize("MEDIUM");
            p3.setIngredients(Arrays.asList(masa, salsa, queso, pepperoni));
            p3.setImageUrl(baseUrl + "pepperoni.png");

            // Pizza Prosciutto Rugula (Masa + Salsa + Queso + Prosciutto + Rúgula)
            Pizza p4 = new Pizza();
            p4.setName("Pizza Prosciutto Rugula");
            p4.setPrice(10000);
            p4.setSize("MEDIUM");
            p4.setIngredients(Arrays.asList(masa, salsa, queso, prosciutto, rugula));
            p4.setImageUrl(baseUrl + "prosciuttorugula.png");

            // Pizza Datterini (Masa + Salsa + Queso + Tomates Cherry)
            Pizza p5 = new Pizza();
            p5.setName("Pizza Datterini");
            p5.setPrice(9000);
            p5.setSize("MEDIUM");
            p5.setIngredients(Arrays.asList(masa, salsa, queso, tomatesCherry));
            p5.setImageUrl(baseUrl + "pizzadatterini.png");

            // Pizza Buffalina (Masa + Salsa + Mozzarella di Bufala + Albahaca)
            Pizza p6 = new Pizza();
            p6.setName("Pizza Buffalina");
            p6.setPrice(10000);
            p6.setSize("MEDIUM");
            p6.setIngredients(Arrays.asList(masa, salsa, bufala, albahaca));
            p6.setImageUrl(baseUrl + "pizzabuffalina.png");

            pizzaRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
            System.out.println("✅ Pizzas cargadas con ingredientes actualizados");
        }

        // clientes
        // Seed test clients if not exists
        if (userRepository.findByUsername("cliente1").isEmpty()) {
            User client1 = new User();
            client1.setUsername("cliente1");
            client1.setPassword(passwordEncoder.encode("cliente123"));
            client1.setFirstName("Juan");
            client1.setLastName("Pérez");
            client1.setRole(Role.CLIENTE);
            client1.setRoles(Set.of(Role.CLIENTE));

            userRepository.save(client1);
            System.out.println("✅ Cliente1 creado (User: cliente1 / Pass: cliente123)");
        }

        if (userRepository.findByUsername("cliente2").isEmpty()) {
            User client2 = new User();
            client2.setUsername("cliente2");
            client2.setPassword(passwordEncoder.encode("cliente123"));
            client2.setFirstName("María");
            client2.setLastName("González");
            client2.setRole(Role.CLIENTE);
            client2.setRoles(Set.of(Role.CLIENTE));

            userRepository.save(client2);
            System.out.println("✅ Cliente2 creado (User: cliente2 / Pass: cliente123)");
        }
    }
}

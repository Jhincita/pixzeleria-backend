package com.pixzeleria.pixzeleria.config;

import com.pixzeleria.pixzeleria.model.Category;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.repository.*;
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
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        try {
            System.out.println("🚀 Iniciando DataSeeder...");

            // 1. Categorías
            if (categoryRepository.count() == 0) {
                Category pizzas = new Category();
                pizzas.setName("Pizzas");
                Category bebidas = new Category();
                bebidas.setName("Bebidas");
                Category ingredientes = new Category();
                ingredientes.setName("Ingredientes");

                categoryRepository.saveAll(Arrays.asList(pizzas, bebidas, ingredientes));
                System.out.println("✅ Categorías cargadas");
            }

            // 2. Ingredientes (solo si no existen)
            if (ingredientRepository.count() == 0) {
                String baseUrl = "https://pub-3108682005f34a1e90099e4d00f82f95.r2.dev/buildyourpizza/";

                // ========== MASAS ==========
                Ingredient masa1 = new Ingredient();
                masa1.setName("Masa Tradicional");
                masa1.setStock(250);
                masa1.setType("masa");
                masa1.setPrice(0);
                masa1.setImageUrl(baseUrl + "masatradicional.svg");

                Ingredient masa2 = new Ingredient();
                masa2.setName("Masa Delgada");
                masa2.setStock(250);
                masa2.setType("masa");
                masa2.setPrice(500);
                masa2.setImageUrl(baseUrl + "masatradicional.svg");

                // ========== SALSAS ==========
                Ingredient salsa1 = new Ingredient();
                salsa1.setName("Salsa de Tomate");
                salsa1.setStock(250);
                salsa1.setType("salsa");
                salsa1.setPrice(0);
                salsa1.setColor("#E53935");
                salsa1.setImageUrl(baseUrl + "tomatosauce.svg");

                Ingredient salsa2 = new Ingredient();
                salsa2.setName("Salsa BBQ");
                salsa2.setStock(250);
                salsa2.setType("salsa");
                salsa2.setPrice(500);
                salsa2.setColor("#5D4037");
                salsa2.setImageUrl(baseUrl + "bbq.svg");

                Ingredient salsa3 = new Ingredient();
                salsa3.setName("Salsa Alfredo");
                salsa3.setStock(250);
                salsa3.setType("salsa");
                salsa3.setPrice(500);
                salsa3.setColor("#F5F5DC");
                salsa3.setImageUrl(baseUrl + "tomatosauce.svg");

                // ========== TOPPINGS ==========
                Ingredient t1 = new Ingredient();
                t1.setName("Queso Mozzarella");
                t1.setStock(250);
                t1.setType("topping");
                t1.setPrice(500);
                t1.setImageUrl(baseUrl + "gratedcheese.svg");

                Ingredient t2 = new Ingredient();
                t2.setName("Pepperoni");
                t2.setStock(250);
                t2.setType("topping");
                t2.setPrice(500);
                t2.setImageUrl(baseUrl + "pepperoni.svg");

                Ingredient t3 = new Ingredient();
                t3.setName("Piña");
                t3.setStock(250);
                t3.setType("topping");
                t3.setPrice(500);
                t3.setImageUrl(baseUrl + "pineapple.svg");

                Ingredient t4 = new Ingredient();
                t4.setName("Prosciutto");
                t4.setStock(250);
                t4.setType("topping");
                t4.setPrice(500);
                t4.setImageUrl(baseUrl + "prosciutto.svg");

                Ingredient t5 = new Ingredient();
                t5.setName("Pistacchio");
                t5.setStock(250);
                t5.setType("topping");
                t5.setPrice(500);
                t5.setImageUrl(baseUrl + "pistacchio.svg");

                Ingredient t6 = new Ingredient();
                t6.setName("Rúgula");
                t6.setStock(250);
                t6.setType("topping");
                t6.setPrice(500);
                t6.setImageUrl(baseUrl + "rgla.svg");

                Ingredient t7 = new Ingredient();
                t7.setName("Tomates Cherry");
                t7.setStock(250);
                t7.setType("topping");
                t7.setPrice(500);
                t7.setImageUrl(baseUrl + "tomato.svg");

                Ingredient t8 = new Ingredient();
                t8.setName("Mozzarella di Bufala");
                t8.setStock(250);
                t8.setType("topping");
                t8.setPrice(1000);
                t8.setImageUrl(baseUrl + "buffala.svg");

                Ingredient t9 = new Ingredient();
                t9.setName("Albahaca");
                t9.setStock(250);
                t9.setType("topping");
                t9.setPrice(300);
                t9.setImageUrl(baseUrl + "albahaca.svg");

                Ingredient t10 = new Ingredient();
                t10.setName("Cebolla");
                t10.setStock(250);
                t10.setType("topping");
                t10.setPrice(300);
                t10.setImageUrl(baseUrl + "onion.svg");

                ingredientRepository.saveAll(Arrays.asList(
                        masa1, masa2,
                        salsa1, salsa2, salsa3,
                        t1, t2, t3, t4, t5, t6, t7, t8, t9, t10
                ));
                System.out.println("✅ Ingredientes cargados: 2 masas, 3 salsas, 10 toppings");
            } else {
                System.out.println("ℹ️ Ingredientes ya existen, saltando...");
            }
            // 3. ADMIN
            User admin = userRepository.findByUsername("admin").orElse(null);

            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("jojo123"));
                admin.setRole(Role.ADMIN);
                admin.setFirstName("Super");
                admin.setLastName("Admin");
                admin.setRoles(Set.of(Role.ADMIN));
                userRepository.save(admin);
                System.out.println("✅ Usuario Admin creado");
            } else {
                System.out.println("ℹ️ Usuario Admin ya existe");
            }

            // Pizzas del Menú (solo si no existen)
            if (pizzaRepository.count() == 0) {
                System.out.println("📦 Cargando pizzas del menú...");

                Ingredient masa = ingredientRepository.findByName("Masa Tradicional");
                Ingredient salsa = ingredientRepository.findByName("Salsa de Tomate");
                Ingredient queso = ingredientRepository.findByName("Queso Mozzarella");
                Ingredient pepperoni = ingredientRepository.findByName("Pepperoni");
                Ingredient prosciutto = ingredientRepository.findByName("Prosciutto");
                Ingredient pistacchio = ingredientRepository.findByName("Pistacchio");
                Ingredient rugula = ingredientRepository.findByName("Rúgula");
                Ingredient tomatesCherry = ingredientRepository.findByName("Tomates Cherry");
                Ingredient bufala = ingredientRepository.findByName("Mozzarella di Bufala");
                Ingredient albahaca = ingredientRepository.findByName("Albahaca");

                String pizzaBaseUrl = "https://pub-3108682005f34a1e90099e4d00f82f95.r2.dev/menu_pizzas/";

                // Margherita
                Pizza p1 = new Pizza();
                p1.setName("Pizza Margherita");
                p1.setPrice(8000);
                p1.setSize("MEDIUM");
                p1.setImageUrl(pizzaBaseUrl + "pizzamargherita.png");
                if (masa != null && salsa != null && queso != null && albahaca != null) {
                    p1.setIngredients(Arrays.asList(masa, salsa, queso, albahaca));
                }

                // Prosciutto Pistacchio
                Pizza p2 = new Pizza();
                p2.setName("Pizza Prosciutto Pistacchio");
                p2.setPrice(10000);
                p2.setSize("MEDIUM");
                p2.setImageUrl(pizzaBaseUrl + "prosciuttopistaccio.png");
                if (masa != null && salsa != null && queso != null && prosciutto != null && pistacchio != null) {
                    p2.setIngredients(Arrays.asList(masa, salsa, queso, prosciutto, pistacchio));
                }

                // Pepperoni
                Pizza p3 = new Pizza();
                p3.setName("Pizza Pepperoni");
                p3.setPrice(9000);
                p3.setSize("MEDIUM");
                p3.setImageUrl(pizzaBaseUrl + "pepperoni.png");
                if (masa != null && salsa != null && queso != null && pepperoni != null) {
                    p3.setIngredients(Arrays.asList(masa, salsa, queso, pepperoni));
                }

                // Prosciutto Rugula
                Pizza p4 = new Pizza();
                p4.setName("Pizza Prosciutto Rugula");
                p4.setPrice(10000);
                p4.setSize("MEDIUM");
                p4.setImageUrl(pizzaBaseUrl + "prosciuttorugula.png");
                if (masa != null && salsa != null && queso != null && prosciutto != null && rugula != null) {
                    p4.setIngredients(Arrays.asList(masa, salsa, queso, prosciutto, rugula));
                }

                // Datterini
                Pizza p5 = new Pizza();
                p5.setName("Pizza Datterini");
                p5.setPrice(9000);
                p5.setSize("MEDIUM");
                p5.setImageUrl(pizzaBaseUrl + "pizzadatterini.png");
                if (masa != null && salsa != null && queso != null && tomatesCherry != null) {
                    p5.setIngredients(Arrays.asList(masa, salsa, queso, tomatesCherry));
                }

                // Buffalina
                Pizza p6 = new Pizza();
                p6.setName("Pizza Buffalina");
                p6.setPrice(10000);
                p6.setSize("MEDIUM");
                p6.setImageUrl(pizzaBaseUrl + "pizzabuffalina.png");
                if (masa != null && salsa != null && bufala != null && albahaca != null) {
                    p6.setIngredients(Arrays.asList(masa, salsa, bufala, albahaca));
                }

                pizzaRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
                System.out.println("✅ Pizzas cargadas: 6 pizzas con nombre y precio");
            } else {
                System.out.println("ℹ️ Pizzas ya existen en la BD: " + pizzaRepository.count() + " pizzas");
            }

            // Clientes de prueba
            if (userRepository.findByUsername("cliente1").isEmpty()) {
                User client1 = new User();
                client1.setUsername("cliente1");
                client1.setPassword(passwordEncoder.encode("cliente123"));
                client1.setFirstName("Juan");
                client1.setLastName("Pérez");
                client1.setRole(Role.CLIENTE);
                userRepository.save(client1);
                System.out.println("✅ Cliente1 creado");
            } else {
                System.out.println("ℹ️ Cliente1 ya existe");
            }

            System.out.println("\n🎉 ¡DataSeeder completado sin errores!");

        } catch (Exception e) {
            System.err.println("❌ ERROR EN DATASEEDER: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
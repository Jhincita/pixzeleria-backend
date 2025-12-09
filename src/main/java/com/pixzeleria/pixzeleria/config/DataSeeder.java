package com.pixzeleria. pixzeleria.config;

import com.pixzeleria.pixzeleria.model.Category;
import com.pixzeleria.pixzeleria.model.menu. Ingredient;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework. boot.CommandLineRunner;
import org.springframework.security.crypto. password.PasswordEncoder;
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
    public void run(String...  args) {
        
        try {
            System.out.println("🚀 Iniciando DataSeeder...");

            // 1. Categorías
            if (categoryRepository.count() == 0) {
                Category pizzas = new Category(); 
                pizzas.setName("Pizzas");
                Category bebidas = new Category(); 
                bebidas. setName("Bebidas");
                Category ingredientes = new Category(); 
                ingredientes.setName("Ingredientes");

                categoryRepository.saveAll(Arrays.asList(pizzas, bebidas, ingredientes));
                System.out.println("✅ Categorías cargadas");
            }

            // 2. Ingredientes (solo si no existen)
            if (ingredientRepository.count() == 0) {
                String baseUrl = "https://pub-3108682005f34a1e90099e4d00f82f95.r2.dev/buildyourpizza/";

                Ingredient i1 = new Ingredient(); 
                i1.setName("Masa Tradicional"); 
                i1.setStock(250); 
                i1.setImageUrl(baseUrl + "masatradicional.svg");
                
                Ingredient i2 = new Ingredient(); 
                i2.setName("Salsa de Tomate"); 
                i2.setStock(250); 
                i2.setImageUrl(baseUrl + "tomatosauce.svg");
                
                Ingredient i3 = new Ingredient(); 
                i3.setName("Queso Mozzarella"); 
                i3.setStock(250); 
                i3.setImageUrl(baseUrl + "gratedcheese.svg");
                
                Ingredient i4 = new Ingredient(); 
                i4.setName("Pepperoni"); 
                i4.setStock(250); 
                i4.setImageUrl(baseUrl + "pepperoni.svg");
                
                Ingredient i5 = new Ingredient(); 
                i5.setName("Piña"); 
                i5.setStock(250); 
                i5.setImageUrl(baseUrl + "pineapple.svg");
                
                Ingredient i6 = new Ingredient(); 
                i6.setName("Prosciutto"); 
                i6.setStock(250); 
                i6.setImageUrl(baseUrl + "prosciutto.svg");
                
                Ingredient i7 = new Ingredient(); 
                i7.setName("Pistacchio"); 
                i7.setStock(250); 
                i7.setImageUrl(baseUrl + "pistacchio.svg");
                
                Ingredient i8 = new Ingredient(); 
                i8.setName("Rúgula"); 
                i8.setStock(250); 
                i8.setImageUrl(baseUrl + "rgla.svg");
                
                Ingredient i9 = new Ingredient(); 
                i9.setName("Tomates Cherry"); 
                i9.setStock(250); 
                i9.setImageUrl(baseUrl + "tomato.svg");
                
                Ingredient i10 = new Ingredient(); 
                i10.setName("Mozzarella di Bufala"); 
                i10.setStock(250); 
                i10.setImageUrl(baseUrl + "buffala.svg");
                
                Ingredient i11 = new Ingredient(); 
                i11.setName("Albahaca"); 
                i11.setStock(250); 
                i11.setImageUrl(baseUrl + "albahaca.svg");
                
                Ingredient i12 = new Ingredient(); 
                i12.setName("Cebolla"); 
                i12.setStock(250); 
                i12.setImageUrl(baseUrl + "onion. svg");
                
                Ingredient i13 = new Ingredient(); 
                i13.setName("Salsa BBQ"); 
                i13.setStock(250); 
                i13.setImageUrl(baseUrl + "bbq.svg");

                ingredientRepository.saveAll(Arrays.asList(
                    i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13
                ));
                System. out.println("✅ Ingredientes cargados:  13 ingredientes");
            } else {
                System.out. println("ℹ️ Ingredientes ya existen, saltando...");
            }

            // 3. ADMIN
            User admin = userRepository.findByUsername("admin").orElse(null);
            
            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder. encode("jojo123"));
                admin.setRole(Role. ADMIN); 
                admin.setFirstName("Super");
                admin.setLastName("Admin");
                admin.setRoles(Set.of(Role.ADMIN)); 
                userRepository.save(admin);
                System.out.println("✅ Usuario Admin creado");
            } else {
                System.out. println("ℹ️ Usuario Admin ya existe");
            }

            // 4. Pizzas del Menú (solo si no existen)
            if (pizzaRepository.count() == 0) {
                System.out.println("📦 Cargando pizzas del menú...");
                
                Ingredient masa = ingredientRepository.findByName("Masa Tradicional");
                Ingredient salsa = ingredientRepository. findByName("Salsa de Tomate");
                Ingredient queso = ingredientRepository.findByName("Queso Mozzarella");
                Ingredient pepperoni = ingredientRepository. findByName("Pepperoni");
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
                p2.setImageUrl(pizzaBaseUrl + "prosciuttopistaccio. png");
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
                p6.setImageUrl(pizzaBaseUrl + "pizzabuffalina. png");
                if (masa != null && salsa != null && bufala != null && albahaca != null) {
                    p6.setIngredients(Arrays. asList(masa, salsa, bufala, albahaca));
                }

                pizzaRepository. saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
                System.out. println("✅ Pizzas cargadas:  6 pizzas con nombre y precio");
            } else {
                System.out.println("ℹ️ Pizzas ya existen en la BD:  " + pizzaRepository.count() + " pizzas");
                System.out.println("⚠️ Si las pizzas tienen datos incorrectos, bórralas manualmente desde Railway");
            }

            // 5. Clientes de prueba
            if (userRepository.findByUsername("cliente1").isEmpty()) {
                User client1 = new User();
                client1.setUsername("cliente1");
                client1.setPassword(passwordEncoder.encode("cliente123"));
                client1.setFirstName("Juan");
                client1.setLastName("Pérez");
                client1.setRole(Role. CLIENTE);
                userRepository. save(client1);
                System.out.println("✅ Cliente1 creado");
            } else {
                System.out. println("ℹ️ Cliente1 ya existe");
            }
            
            System.out.println("\n🎉 ¡DataSeeder completado sin errores!");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR EN DATASEEDER: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
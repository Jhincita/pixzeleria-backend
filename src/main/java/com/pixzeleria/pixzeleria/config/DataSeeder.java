package com.pixzeleria.pixzeleria.config;

import com.pixzeleria.pixzeleria.model.User;
import com.pixzeleria.pixzeleria.model.Role;
import com.pixzeleria.pixzeleria.model.Category;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
<<<<<<< Updated upstream
=======
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.model.user.Role;
import com.pixzeleria.pixzeleria.model.user.User;
>>>>>>> Stashed changes
import com.pixzeleria.pixzeleria.repository.CategoryRepository;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PizzaRepository pizzaRepository;

    @Override
    public void run(String... args) throws Exception {
<<<<<<< Updated upstream
        
=======

>>>>>>> Stashed changes
        if (categoryRepository.count() == 0) {
            Category pizzas = new Category(); pizzas.setName("Pizzas");
            Category bebidas = new Category(); bebidas.setName("Bebidas");
            Category ingredientes = new Category(); ingredientes.setName("Ingredientes");
            
            categoryRepository.saveAll(Arrays.asList(pizzas, bebidas, ingredientes));
            System.out.println("✅ Categorías cargadas");
        }

        if (ingredientRepository.count() == 0) {
            Ingredient i1 = new Ingredient(); i1.setName("Masa Tradicional"); i1.setStock(250);
            Ingredient i2 = new Ingredient(); i2.setName("Masa Piedra"); i2.setStock(250);
            Ingredient i3 = new Ingredient(); i3.setName("Salsa de Tomate"); i3.setStock(250);
            Ingredient i4 = new Ingredient(); i4.setName("Salsa BBQ"); i4.setStock(250);
            Ingredient i5 = new Ingredient(); i5.setName("Queso Mozzarella"); i5.setStock(250);
            Ingredient i6 = new Ingredient(); i6.setName("Tomate"); i6.setStock(250);
            Ingredient i7 = new Ingredient(); i7.setName("Pepperoni"); i7.setStock(250);
            Ingredient i8 = new Ingredient(); i8.setName("Champiñones"); i8.setStock(250);
            Ingredient i9 = new Ingredient(); i9.setName("Cebolla"); i9.setStock(250);
            Ingredient i10 = new Ingredient(); i10.setName("Prosciutto"); i10.setStock(250);
            Ingredient i11 = new Ingredient(); i11.setName("Albahaca"); i11.setStock(250);
            Ingredient i12 = new Ingredient(); i12.setName("Piña"); i12.setStock(250);

<<<<<<< Updated upstream
            ingredientRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5));
            System.out.println("Ingredientes cargados");
        }

=======
            ingredientRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12));
            System.out.println("✅ Ingredientes cargados");
        }

        if (pizzaRepository.count() == 0) {
            Ingredient masa = ingredientRepository.findByName("Masa Tradicional");
            Ingredient masaPiedra = ingredientRepository.findByName("Masa Piedra");
            Ingredient salsa = ingredientRepository.findByName("Salsa de Tomate");
            Ingredient salsaBBQ = ingredientRepository.findByName("Salsa BBQ");
            Ingredient queso = ingredientRepository.findByName("Queso Mozzarella");
            Ingredient pepperoni = ingredientRepository.findByName("Pepperoni");
            Ingredient tomate = ingredientRepository.findByName("Tomate");
            Ingredient champiñones = ingredientRepository.findByName("Champiñones");
            Ingredient cebolla = ingredientRepository.findByName("Cebolla");
            Ingredient prosciutto = ingredientRepository.findByName("Prosciutto");
            Ingredient pollo = ingredientRepository.findByName("Pollo");

            Pizza margherita = new Pizza();
            margherita.setName("Pizza Margherita");
            margherita.setPrice(8000);
            margherita.setSize("Mediana");
            margherita.setImageUrl("/menuimg/pizzamargherita.png");
            margherita.setPixelImageUrl("/menuimg_hover/pizzamargherita.png");
            margherita.setIngredients(Arrays.asList(masa, salsa, queso));
            margherita.setCategory(categoryRepository.findAll().get(0));

            Pizza proscipizza = new Pizza();
            proscipizza.setName("Pizza Prosciutto");
            proscipizza.setPrice(10000);
            proscipizza.setSize("Mediana");
            proscipizza.setImageUrl("/menuimg/prosciuttopistaccio.png");
            proscipizza.setPixelImageUrl("/menuimg_hover/prosciuttopistaccio.png");
            proscipizza.setIngredients(Arrays.asList(masa, salsa, queso, prosciutto));
            proscipizza.setCategory(categoryRepository.findAll().get(0));

            Pizza pepp = new Pizza();
            pepp.setName("Pizza Pepperoni");
            pepp.setPrice(9000);
            pepp.setSize("Mediana");
            pepp.setImageUrl("/menuimg/pepperoni.png");
            pepp.setIngredients(Arrays.asList(masa, salsa, queso, pepperoni));
            pepp.setCategory(categoryRepository.findAll().get(0));

            Pizza dattPizza = new Pizza();
            dattPizza.setName("Pizza Datterini");
            dattPizza.setPrice(9500);
            dattPizza.setSize("Mediana");
            dattPizza.setImageUrl("/menuimg/pizzadatterini.png");
            dattPizza.setIngredients(Arrays.asList(masaPiedra, salsa, queso, tomate, champiñones, cebolla));
            dattPizza.setCategory(categoryRepository.findAll().get(0));

            Pizza buffPizza = new Pizza();
            buffPizza.setName("Pizza Buffalina");
            buffPizza.setPrice(11000);
            buffPizza.setSize("Mediana");
            buffPizza.setImageUrl("/menuimg/pizzabuffalina.png");
            buffPizza.setIngredients(Arrays.asList(masaPiedra, salsaBBQ, queso, pollo, cebolla, tomate));
            buffPizza.setCategory(categoryRepository.findAll().get(0));

            pizzaRepository.saveAll(Arrays.asList(margherita, pepp, proscipizza, dattPizza, buffPizza));
            System.out.println("✅ Pizzas del Menú cargadas");
        }

>>>>>>> Stashed changes
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Supremo");
            admin.setUsername("admin");
<<<<<<< Updated upstream
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Usuario Admin creado (User: admin / Pass: 123)");
=======
            admin.setPassword(passwordEncoder.encode("oyasumi123")); // default password
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("✅ Usuario Admin creado (User: admin / Pass: oyasumi123)");
>>>>>>> Stashed changes
        }
    }
}
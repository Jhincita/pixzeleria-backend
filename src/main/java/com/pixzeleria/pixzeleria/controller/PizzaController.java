import com.pixzeleria.pixzeleria.model.Pizza;
import com.pixzeleria.pixzeleria.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    // Obtener todas las pipshas
    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }

    // Obtener pizza por id
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar nueva pipsha
    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) {
        return ResponseEntity.ok(pizzaService.savePizza(pizza));
    }

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody PizzaDTO dto) {
        // Le pasamos el DTO al servicio
        return ResponseEntity.ok(pizzaService.savePizza(dto)); 
    }
    
    // Eliminar pizza
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}
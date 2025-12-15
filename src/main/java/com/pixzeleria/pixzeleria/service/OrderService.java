package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.OrderDTO;
import com.pixzeleria.pixzeleria.dto.OrderPizzaDTO;
import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com.pixzeleria.pixzeleria.model.menu.Ingredient;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.model.order.Order;
import com.pixzeleria.pixzeleria.model.order.OrderIngredient;
import com.pixzeleria.pixzeleria.model.order.OrderItem;
import com.pixzeleria.pixzeleria.model.order.OrderPizza;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.IngredientRepository;
import com.pixzeleria.pixzeleria.repository.OrderRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(OrderRequest request) {
        // Get authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // Create order
        Order order = new Order();
        order.setClient(user);
        order.setItems(new ArrayList<>());

        // Process pizzas
        if (request.getPizzas() != null && !request.getPizzas().isEmpty()) {
            for (OrderPizzaDTO pizzaDto : request.getPizzas()) {
                OrderPizza orderPizza = createOrderPizza(pizzaDto, order);
                order.getItems().add(orderPizza);
            }
        }

        // Save and return
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    private OrderPizza createOrderPizza(OrderPizzaDTO dto, Order order) {
        OrderPizza orderPizza = new OrderPizza();
        orderPizza.setOrder(order);
        orderPizza.setQuantity(dto.getQuantity() > 0 ? dto.getQuantity() : 1);
        orderPizza.setIngredients(new ArrayList<>());

        if (dto.isCustom()) {
            // === CUSTOM PIZZA ===
            orderPizza.setBasePizza(null);
            orderPizza.setFinalPrice(dto.getTotalPrice());
            orderPizza.setPrice(dto.getTotalPrice());

            // Add ingredients
            if (dto.getIngredientIds() != null && !dto.getIngredientIds().isEmpty()) {
                List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());

                for (Ingredient ingredient : ingredients) {
                    OrderIngredient orderIngredient = new OrderIngredient();
                    orderIngredient.setOrderPizza(orderPizza);
                    orderIngredient.setIngredient(ingredient);
                    orderIngredient.setQuantity(1);
                    orderIngredient.setAdded(true);
                    orderIngredient.setRemoved(false);
                    orderPizza.getIngredients().add(orderIngredient);
                }
            }
        } else {
            // === MENU PIZZA ===
            Pizza menuPizza = pizzaRepository.findById(dto.getMenuPizzaId())
                    .orElseThrow(() -> new RuntimeException("Pizza no encontrada: " + dto.getMenuPizzaId()));

            orderPizza.setBasePizza(menuPizza);
            orderPizza.setProduct(menuPizza);
            orderPizza.setFinalPrice((double) menuPizza.getPrice());
            orderPizza.setPrice(menuPizza.getPrice());
        }

        return orderPizza;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> dtos = new ArrayList<>();

        for (Order order : orders) {
            dtos.add(mapToDTO(order));
        }

        return dtos;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(Order order) {
        double total = 0;
        List<OrderDTO.OrderItemDTO> itemDTOs = new ArrayList<>();

        if (order.getItems() != null) {
            for (var item : order.getItems()) {
                String productName;

                if (item instanceof OrderPizza) {
                    OrderPizza op = (OrderPizza) item;
                    if (op.getBasePizza() != null) {
                        productName = op.getBasePizza().getName();
                    } else {
                        productName = "Pizza Personalizada";
                    }
                } else if (item.getProduct() != null) {
                    productName = item.getProduct().getName();
                } else {
                    productName = "Producto desconocido";
                }

                double subtotal = item.getPrice() * item.getQuantity();

                OrderDTO.OrderItemDTO itemDTO = OrderDTO.OrderItemDTO.builder()
                        .id(item.getId())
                        .productName(productName)
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(subtotal)
                        .build();

                itemDTOs.add(itemDTO);
                total += subtotal;
            }
        }

        return OrderDTO.builder()
                .id(order.getId())
                .clientName(order.getClient() != null ? order.getClient().getUsername() : "Anónimo")
                .items(itemDTOs)
                .total(total)
                .build();
    }
}
package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com.pixzeleria.pixzeleria.model.User;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.model.order.Order;
import com.pixzeleria.pixzeleria.model.order.OrderItem;
import com.pixzeleria.pixzeleria.repository.OrderRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    public Order createOrder(OrderRequest request) {
        // 1. Obtener el usuario logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. Crear la orden base
        Order order = new Order();
        order.setClient(user);
        order.setItems(new ArrayList<>());

        // 3. Agregar pizzas del menú (si vienen en el pedido)
        if (request.getMenuPizzaIds() != null && !request.getMenuPizzaIds().isEmpty()) {
            List<Pizza> pizzas = pizzaRepository.findAllById(request.getMenuPizzaIds());

            // Aquí estaba el error probable: el bucle for
            for (Pizza pizza : pizzas) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(pizza);
                item.setQuantity(1);
                item.setPrice(pizza.getPrice());
                
                order.getItems().add(item);
            }
        }

        // 4. Guardar
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
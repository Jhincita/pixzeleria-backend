package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com.pixzeleria.pixzeleria.model.user.User;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    public Order createOrder(OrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Order order = new Order();
        order.setClient(user);
        order.setItems(new ArrayList<>());

        if (request.getMenuPizzaIds() != null && !request.getMenuPizzaIds().isEmpty()) {

            Map<Long, Long> quantities = request.getMenuPizzaIds().stream()
                    .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

            List<Pizza> pizzas = pizzaRepository.findAllById(quantities.keySet());

            for (Pizza pizza : pizzas) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(pizza);
                item.setQuantity(quantities.get(pizza.getId()).intValue());
                item.setPrice(pizza.getPrice());
                order.getItems().add(item);
            }
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
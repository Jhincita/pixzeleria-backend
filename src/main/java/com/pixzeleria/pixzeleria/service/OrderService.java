package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.OrderDTO;
import com.pixzeleria.pixzeleria.dto. OrderRequest;
import com.pixzeleria.pixzeleria.model.menu.Pizza;
import com.pixzeleria.pixzeleria.model.order.Order;
import com.pixzeleria.pixzeleria.model.order.OrderItem;
import com.pixzeleria.pixzeleria.model. user.User;
import com. pixzeleria.pixzeleria. repository.OrderRepository;
import com.pixzeleria.pixzeleria.repository.PizzaRepository;
import com. pixzeleria.pixzeleria. repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security. core.context.SecurityContextHolder;
import org.springframework.stereotype. Service;
import org.springframework. transaction.annotation.Transactional;

import java.util. ArrayList;
import java.util. List;
import java.util. Map;
import java.util. function.Function;
import java. util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(OrderRequest request) {
        String username = SecurityContextHolder. getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        Order order = new Order();
        order.setClient(user);
        order.setItems(new ArrayList<>());

        Map<Long, Long> quantityMap = request.getMenuPizzaIds().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<Long, Long> entry : quantityMap. entrySet()) {
            Long pizzaId = entry.getKey();
            int quantity = entry. getValue().intValue();

            Pizza pizza = pizzaRepository.findById(pizzaId)
                    .orElseThrow(() -> new RuntimeException("Pizza no encontrada ID: " + pizzaId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(pizza);
            item.setQuantity(quantity);
            item.setPrice(pizza.getPrice());
            
            order. getItems().add(item);
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(Order order) {
        double total = order.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        List<OrderDTO.OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> OrderDTO.OrderItemDTO.builder()
                        . id(item.getId())
                        .productName(item.getProduct().getName())
                        . quantity(item.getQuantity())
                        .price(item. getPrice())
                        .subtotal(item.getPrice() * item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .clientName(order. getClient() != null ? order.getClient().getUsername() : "Anonimo")
                .items(itemDTOs)
                .total(total)
                .build();
    }
}
package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto.OrderDTO;
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
import java.util.stream.Collectors;

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

        // 3. Agregar pizzas del menú
        if (request.getMenuPizzaIds() != null && !request.getMenuPizzaIds().isEmpty()) {
            List<Pizza> pizzas = pizzaRepository.findAllById(request.getMenuPizzaIds());
            
            for (Pizza pizza : pizzas) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(pizza);
                item.setQuantity(1);
                item.setPrice(8000.0);
                
                order.getItems().add(item);
            }
        }

        // 4. Guardar
        return orderRepository.save(order);
    }

    public List<OrderDTO> getAllOrdersDTO() {
        List<Order> orders = orderRepository.findAll();
        
        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Mantener el método original por compatibilidad
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private OrderDTO convertToDTO(Order order) {
    // Calcular items
    List<OrderDTO.OrderItemDTO> itemDTOs = order.getItems().stream()
        .map(item -> {
            String productName = "Producto desconocido";
            
            if (item.getProduct() instanceof Pizza) {
                Pizza pizza = (Pizza) item.getProduct();
                productName = pizza.getName();
            }
            
            return OrderDTO.OrderItemDTO.builder()
                .id(item.getId())
                .productName(productName)
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getPrice() * item.getQuantity())
                .build();
        })
        .collect(Collectors.toList());

    // Calcular total
    double total = itemDTOs.stream()
        .mapToDouble(OrderDTO.OrderItemDTO::getSubtotal)
        .sum();

    // Construir DTO
    return OrderDTO.builder()
        .id(order.getId())
        .clientName(order.getClient() != null ? 
            order.getClient().getFirstName() + " " + order.getClient().getLastName() : 
            "Cliente desconocido")
        .items(itemDTOs)
        .total(total)
        .build();
}
}
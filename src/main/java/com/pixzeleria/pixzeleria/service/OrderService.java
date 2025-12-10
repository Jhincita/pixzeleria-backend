package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.dto. OrderDTO;
import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com. pixzeleria.pixzeleria. model.menu.Pizza;
import com.pixzeleria.pixzeleria.model.order.Order;
import com.pixzeleria.pixzeleria.model.order.OrderItem;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.OrderRepository;
import com. pixzeleria.pixzeleria. repository.PizzaRepository;
import com. pixzeleria.pixzeleria. repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util. ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(OrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado:  " + username));

        Order order = new Order();
        order.setClient(user);
        order.setItems(new ArrayList<>());

        Map<Long, Long> quantityMap = request.getMenuPizzaIds().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<Long, Long> entry : quantityMap.entrySet()) {
            Long pizzaId = entry.getKey();
            int quantity = entry.getValue().intValue();

            Pizza pizza = pizzaRepository. findById(pizzaId)
                    .orElseThrow(() -> new RuntimeException("Pizza no encontrada ID: " + pizzaId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(pizza);
            item.setQuantity(quantity);
            item.setPrice(pizza.getPrice());
            
            order.getItems().add(item);
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        try {
            System.out.println("🔍 Buscando órdenes en la BD...");
            List<Order> orders = orderRepository.findAll();
            System.out.println("📦 Órdenes encontradas:  " + orders.size());
            
            List<OrderDTO> dtos = new ArrayList<>();
            for (Order order : orders) {
                try {
                    System.out. println("  🔄 Procesando orden ID: " + order.getId());
                    OrderDTO dto = mapToDTO(order);
                    dtos. add(dto);
                    System.out.println("  ✅ Orden " + order.getId() + " mapeada exitosamente");
                } catch (Exception e) {
                    System.err.println("  ❌ Error mapeando orden ID:  " + order.getId());
                    e.printStackTrace();
                    throw e; // Re-lanzar para ver el stacktrace completo
                }
            }
            
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Error en getAllOrders:");
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(Order order) {
        try {
            System.out.println("    🔍 Mapeando orden:  " + order.getId());
            
            // Verificar client
            User client = order.getClient();
            System.out.println("    👤 Cliente: " + (client != null ? client.getUsername() : "null"));
            
            // Verificar items
            List<OrderItem> items = order.getItems();
            System. out.println("    📦 Items: " + (items != null ? items.size() : "null"));
            
            double total = 0;
            List<OrderDTO.OrderItemDTO> itemDTOs = new ArrayList<>();
            
            if (items != null) {
                for (OrderItem item : items) {
                    System.out.println("      🍕 Item ID: " + item.getId());
                    System.out. println("      🍕 Product: " + (item.getProduct() != null ? item.getProduct().getName() : "null"));
                    
                    OrderDTO.OrderItemDTO itemDTO = OrderDTO.OrderItemDTO.builder()
                            .id(item. getId())
                            .productName(item.getProduct() != null ? item.getProduct().getName() : "Producto desconocido")
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            . subtotal(item.getPrice() * item.getQuantity())
                            .build();
                    
                    itemDTOs.add(itemDTO);
                    total += item. getPrice() * item.getQuantity();
                }
            }
            
            OrderDTO dto = OrderDTO.builder()
                    .id(order. getId())
                    .clientName(client != null ? client. getUsername() : "Anónimo")
                    .items(itemDTOs)
                    .total(total)
                    .build();
            
            System.out.println("    ✅ DTO creado para orden: " + order.getId());
            return dto;
            
        } catch (Exception e) {
            System.err.println("    ❌ Error en mapToDTO:");
            e.printStackTrace();
            throw e;
        }
    }
}
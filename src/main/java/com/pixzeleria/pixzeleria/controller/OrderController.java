package com.pixzeleria.pixzeleria.controller;

import com. pixzeleria.pixzeleria. dto.OrderDTO;
import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com.pixzeleria.pixzeleria.service.OrderService;
import lombok. RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework. web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService. createOrder(request));
    }

    @GetMapping
    public ResponseEntity<? > getAllOrders() {
        try {
            System.out.println("🔍 Intentando obtener todas las órdenes...");
            List<OrderDTO> orders = orderService.getAllOrders();
            System.out.println("✅ Órdenes obtenidas:  " + orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            System.err.println("❌ ERROR EN GETALLORDERS:");
            e.printStackTrace();
            
            Map<String, String> error = new HashMap<>();
            error. put("error", e.getClass().getName());
            error.put("message", e.getMessage());
            error.put("cause", e.getCause() != null ? e.getCause().toString() : "null");
            
            return ResponseEntity.status(500).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
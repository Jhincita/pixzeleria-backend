package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.dto.OrderRequest;
import com.pixzeleria.pixzeleria.model.order.Order;
import com.pixzeleria.pixzeleria.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        System.out.println("📢 ¡CHIBI! ¡ENTRAMOS AL GET DE ÓRDENES! 📢");
    return ResponseEntity.ok(orderService.getAllOrders());
}
}
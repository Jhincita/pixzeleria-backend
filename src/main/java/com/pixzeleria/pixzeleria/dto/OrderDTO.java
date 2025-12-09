package com.pixzeleria.pixzeleria.dto;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private String clientName;
    private List<OrderItemDTO> items;
    private double total;
    
    @Data
    @Builder
    public static class OrderItemDTO {
        private Long id;
        private String productName;
        private int quantity;
        private double price;
        private double subtotal;
    }
}
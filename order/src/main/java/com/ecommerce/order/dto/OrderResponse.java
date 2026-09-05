package com.ecommerce.order.dto;

import com.ecommerce.order.Entity.OrderStatus;
import com.ecommerce.order.dto.OrderItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;

}

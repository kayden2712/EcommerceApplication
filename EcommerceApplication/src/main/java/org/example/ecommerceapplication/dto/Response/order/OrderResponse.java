package org.example.ecommerceapplication.dto.Response.order;

import org.example.ecommerceapplication.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        BigDecimal totalPrice,
        OrderStatus status,
        List<OrderItemResponse> items
) {}


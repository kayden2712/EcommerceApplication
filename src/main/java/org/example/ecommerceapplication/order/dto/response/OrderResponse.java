package org.example.ecommerceapplication.order.dto.response;

import org.example.ecommerceapplication.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        BigDecimal totalPrice,
        OrderStatus status,
        List<OrderItemResponse> items
) {}


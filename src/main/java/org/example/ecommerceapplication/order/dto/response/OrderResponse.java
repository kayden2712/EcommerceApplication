package org.example.ecommerceapplication.order.dto.response;

import java.math.BigDecimal;
import java.util.List;

import org.example.ecommerceapplication.common.enums.OrderStatus;

public record OrderResponse(
        Long id,
        BigDecimal totalPrice,
        OrderStatus status,
        List<OrderItemResponse> items
) {}


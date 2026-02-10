package org.example.ecommerceapplication.dto.Response.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal totalPrice) {
}

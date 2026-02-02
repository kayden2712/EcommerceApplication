package org.example.ecommerceapplication.dto.Response.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId, String productName, BigDecimal price, Integer quantity) {
}

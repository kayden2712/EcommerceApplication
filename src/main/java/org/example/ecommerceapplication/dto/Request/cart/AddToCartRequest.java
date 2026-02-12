package org.example.ecommerceapplication.dto.Request.cart;

public record AddToCartRequest(Long userId, Long productId, Integer quantity, String selectedColor) {
}

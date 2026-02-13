package org.example.ecommerceapplication.dto.Request.cart;

public record AddToCartRequest(Long productId, Integer quantity, String selectedColor) {
}

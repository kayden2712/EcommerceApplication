package org.example.ecommerceapplication.cart.dto;

public record AddToCartRequest(Long productId, Integer quantity, String selectedColor) {
}

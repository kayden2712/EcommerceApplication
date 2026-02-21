package org.example.ecommerceapplication.cart.dto;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        Double price,
        Integer quantity,
        String selectedColor,
        String availableColor
) {
}

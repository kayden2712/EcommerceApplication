package org.example.ecommerceapplication.dto.Response.cart;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        Double price,
        Integer quantity
) {
}

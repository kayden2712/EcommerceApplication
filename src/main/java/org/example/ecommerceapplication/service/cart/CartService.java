package org.example.ecommerceapplication.service.cart;

import org.example.ecommerceapplication.dto.Request.cart.AddToCartRequest;

public interface CartService {
    void addToCart(AddToCartRequest request);

    void removeItem(Long userId, Long productId);

    void clearCart(Long userId);

    void updateCart(Long userId, Long productId, Integer quantity);
}

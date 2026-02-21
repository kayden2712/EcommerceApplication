package org.example.ecommerceapplication.cart.service;

import java.util.List;

import org.example.ecommerceapplication.cart.dto.AddToCartRequest;
import org.example.ecommerceapplication.cart.dto.CartItemResponse;

public interface CartService {

    void addToCart(String username, AddToCartRequest request);

    void updateCart(String username, Long productId, Integer quantity);

    CartItemResponse getCartItem(String username, Long productId);

    List<CartItemResponse> getAllCartItems(String username);

    void removeItem(String username, Long productId);

    void clearCart(String username);
}

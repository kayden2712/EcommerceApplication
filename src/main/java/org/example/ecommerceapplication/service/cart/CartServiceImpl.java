package org.example.ecommerceapplication.service.cart;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.dto.Request.cart.AddToCartRequest;
import org.example.ecommerceapplication.entity.Cart;
import org.example.ecommerceapplication.entity.CartItem;
import org.example.ecommerceapplication.entity.Product;
import org.example.ecommerceapplication.repository.CartItemRepository;
import org.example.ecommerceapplication.repository.CartRepository;
import org.example.ecommerceapplication.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void addToCart(AddToCartRequest request) {
        Cart cart = getCartByUserId(request.userId());
        Product product = getByProductId(request.productId());
        int quantity = request.quantity();

        validateQuantity(quantity);
        validateStock(product, quantity);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.productId()).orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItem(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

//    @Override
//    @Transactional(readOnly = true)
//    public CartResponse getCart(Long userId) {
//        return getCartByUserId(userId);
//    }

    @Override
    public void updateCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = getByProductId(productId);

        validateQuantity(quantity);
        validateStock(product, quantity);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    private Cart getCartByUserId(Long userId) {
        return cartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));
    }

    private Product getByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }

    private void validateStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product id: " + product.getId());
        }
    }

}

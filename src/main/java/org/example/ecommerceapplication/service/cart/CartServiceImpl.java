package org.example.ecommerceapplication.service.cart;

import java.util.List;
import java.util.Optional;

import org.example.ecommerceapplication.Mapper.CartItemMapper;
import org.example.ecommerceapplication.dto.Request.cart.AddToCartRequest;
import org.example.ecommerceapplication.dto.Response.cart.CartItemResponse;
import org.example.ecommerceapplication.entity.Cart;
import org.example.ecommerceapplication.entity.CartItem;
import org.example.ecommerceapplication.entity.Product;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.repository.CartItemRepository;
import org.example.ecommerceapplication.repository.CartRepository;
import org.example.ecommerceapplication.repository.ProductRepository;
import org.example.ecommerceapplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;
    private final UserRepository userRepository;

    @Override
    public void addToCart(String username, AddToCartRequest request) {

        User user = getUser(username);
        Product product = getProduct(request.productId());
        Cart cart = getOrCreateCart(user);

        validateQuantity(request.quantity());

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        int newQuantity = (cartItem != null)
                ? cartItem.getQuantity() + request.quantity()
                : request.quantity();

        validateStock(product, newQuantity);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(newQuantity);
        cartItem.setSelectedColor(request.selectedColor());

        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItem(String username, Long productId) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        CartItem cartItem = getCartItemByCartIdAndProductId(cart.getId(), productId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String username) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    @Override
    public void updateCart(String username, Long productId, Integer quantity) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        Product product = getProduct(productId);

        validateQuantity(quantity);
        validateStock(product, quantity);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional(readOnly = true)
    public CartItemResponse getCartItem(String username, Long productId) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        return cartItemMapper.toResponse(getCartItemByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponse> getAllCartItems(String username) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        return cartItemRepository.findAllByCartId(cart.getId()).stream()
                .map(cartItemMapper::toResponse)
                .toList();
    }

    // Fetch or create cart for user
    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    // Fetch product by ID
    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));
    }

    // Fetch user by username
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    // Fetch cart item by cart ID and product ID
    private Optional<CartItem> getCartItemByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    // Validate quantity
    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }

    // Validate stock availability
    private void validateStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product id: " + product.getId());
        }
    }

}

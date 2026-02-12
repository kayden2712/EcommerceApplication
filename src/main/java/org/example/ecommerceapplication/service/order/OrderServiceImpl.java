package org.example.ecommerceapplication.service.order;

import java.math.BigDecimal;
import java.util.List;

import org.example.ecommerceapplication.Mapper.OrderMapper;
import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.entity.Cart;
import org.example.ecommerceapplication.entity.CartItem;
import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.entity.OrderItem;
import org.example.ecommerceapplication.entity.Product;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.enums.OrderStatus;
import org.example.ecommerceapplication.repository.CartItemRepository;
import org.example.ecommerceapplication.repository.CartRepository;
import org.example.ecommerceapplication.repository.OrderRepository;
import org.example.ecommerceapplication.repository.ProductRepository;
import org.example.ecommerceapplication.repository.UserRepository;
import org.example.ecommerceapplication.service.cart.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final Validator validator;
    private final OrderPricingService orderPricingService;
    private final OrderMapper orderMapper;
    private final CartService cartService;

    @Override
    public OrderResponse placeOrder(Long userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItems = getCartItems(cart);

        Order order = createPendingOrder(cart);

        List<OrderItem> orderItems = buildOrder(order, cartItems);
        order.setItems(orderItems);
        //Pricing
        BigDecimal totalPrice = orderPricingService.calculateOrderTotal(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getUser().getUsername());

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        validator.validateCancel(order);
        restoreStock(order);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        return orderMapper.toResponse(getOrderById(orderId));
    }

    // Fetch user by ID
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found with id: " + userId));
    }

    // Fetch cart by user ID
    private Cart getCartByUserId(Long userId) {
        User user = getUser(userId);
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user id: " + userId));
    }

    // Fetch cart items
    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty for cart id: " + cart.getId());
        }
        return cartItems;
    }

    // Create a new order with PENDING status
    private Order createPendingOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    // Build order items from cart items
    private List<OrderItem> buildOrder(Order order, List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    int quantity = cartItem.getQuantity();
                    validateStock(product, quantity);
                    product.setStock(product.getStock() - quantity);
                    productRepository.save(product);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(quantity);
                    orderItem.setPrice(product.getPrice());
                    return orderItem;
                })
                .toList();
    }

    // Validate stock availability
    private void validateStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getId());
        }
    }

    // Fetch order by ID
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found for id: " + orderId));
    }

    // Restore stock when order is cancelled
    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
    }

}

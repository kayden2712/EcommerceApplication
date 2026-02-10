package org.example.ecommerceapplication.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.Mapper.OrderMapper;
import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.entity.*;
import org.example.ecommerceapplication.enums.OrderStatus;
import org.example.ecommerceapplication.repository.CartItemRepository;
import org.example.ecommerceapplication.repository.CartRepository;
import org.example.ecommerceapplication.repository.OrderRepository;
import org.example.ecommerceapplication.repository.ProductRepository;
import org.example.ecommerceapplication.service.cart.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private final Validator validator;
    private final OrderPricingService orderPricingService;
    private final OrderMapper orderMapper;
    private final CartService cartService;

    @Override
    public OrderResponse placeOrder(Long userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItems = getCartItems(cart);

        Order order = createPendingOrder(cart);

        List<OrderItem> orderItems = buidOrder(order, cartItems);
        order.setItems(orderItems);
        //Pricing
        BigDecimal totalPrice = orderPricingService.calculateOrderTotal(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getUser().getId());

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


    //Method Helpers
    private Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user id: " + userId));
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty for cart id: " + cart.getId());
        }
        return cartItems;
    }

    private Order createPendingOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    private List<OrderItem> buidOrder(Order order, List<CartItem> cartItems) {
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


    private void validateStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getId());
        }
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found for id: " + orderId));
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
    }

}

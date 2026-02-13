package org.example.ecommerceapplication.controller.order;

import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.service.order.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse placeOrder(Authentication authentication) {
        return orderService.placeOrder(authentication.getName());
    }

    @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Long orderId, Authentication authentication) {
        orderService.cancelOrder(orderId, authentication.getName());
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId, Authentication authentication) {
        return orderService.getOrder(orderId, authentication.getName());
    }
}

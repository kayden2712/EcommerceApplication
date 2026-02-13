package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Điều phối các hoạt động liên quan đến đơn hàng
public interface OrderService {
    OrderResponse placeOrder(String name);

    void cancelOrder(Long orderId,String name);

    OrderResponse getOrder(Long orderId, String name);

    Page<OrderResponse> getOrderHistory(String name, OrderStatus status, Pageable pageable);
}

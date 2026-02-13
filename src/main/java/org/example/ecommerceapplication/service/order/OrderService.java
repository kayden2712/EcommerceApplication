package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.dto.Response.order.OrderResponse;

//Điều phối các hoạt động liên quan đến đơn hàng
public interface OrderService {
    OrderResponse placeOrder(String name);

    void cancelOrder(Long orderId,String name);

    OrderResponse getOrder(Long orderId, String name);
}

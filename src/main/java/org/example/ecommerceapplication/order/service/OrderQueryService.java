package org.example.ecommerceapplication.order.service;

import java.util.List;

import org.example.ecommerceapplication.order.dto.response.OrderResponse;
//READ riêng v
//cache dễ hơn, scale tốt hơn
public interface OrderQueryService {

    OrderResponse getById(Long id);

    List<OrderResponse> getByUser(Long id);
}

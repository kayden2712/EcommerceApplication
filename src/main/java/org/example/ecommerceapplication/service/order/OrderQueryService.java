package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.dto.Response.order.OrderResponse;

import java.util.List;
//READ riêng v
//cache dễ hơn, scale tốt hơn
public interface OrderQueryService {

    OrderResponse getById(Long id);

    List<OrderResponse> getByUser(Long id);
}

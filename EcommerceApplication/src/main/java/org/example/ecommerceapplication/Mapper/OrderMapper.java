package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Response.order.OrderItemResponse;
import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Order -> OrderResponse
    OrderResponse toResponse(Order order);

    // OrderItem -> OrderItemResponse
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse toItemResponse(OrderItem orderItem);
}

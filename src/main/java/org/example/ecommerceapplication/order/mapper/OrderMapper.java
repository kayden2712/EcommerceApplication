package org.example.ecommerceapplication.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.example.ecommerceapplication.order.dto.response.OrderItemResponse;
import org.example.ecommerceapplication.order.dto.response.OrderResponse;
import org.example.ecommerceapplication.order.entity.Order;
import org.example.ecommerceapplication.order.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "price", target = "unitPrice")
    OrderItemResponse toItemResponse(OrderItem orderItem);
}

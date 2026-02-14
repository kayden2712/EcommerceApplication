package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Response.order.OrderItemResponse;
import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "price", target = "unitPrice")
    OrderItemResponse toItemResponse(OrderItem orderItem);
}

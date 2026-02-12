package org.example.ecommerceapplication.Mapper;

import java.util.List;

import org.example.ecommerceapplication.dto.Response.cart.CartItemResponse;
import org.example.ecommerceapplication.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "selectedColor", target = "selectedColor")
    @Mapping(source = "product.color", target = "availableColor")
    CartItemResponse toResponse(CartItem cartItem);

    List<CartItemResponse> toResponseList(List<CartItem> cartItems);
}

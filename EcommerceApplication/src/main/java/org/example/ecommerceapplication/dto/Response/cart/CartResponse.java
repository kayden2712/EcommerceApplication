package org.example.ecommerceapplication.dto.Response.cart;

import java.util.List;

public record CartResponse(Long cartId, List<CartItemResponse> items) { }

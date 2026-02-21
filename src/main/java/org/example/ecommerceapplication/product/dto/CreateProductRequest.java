package org.example.ecommerceapplication.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;

public record CreateProductRequest(
        String name,

        @DecimalMin("0.0")
        BigDecimal price,

        Integer stock,

        String color,

        Long categoryId
) {
}


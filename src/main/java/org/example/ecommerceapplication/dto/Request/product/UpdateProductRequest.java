package org.example.ecommerceapplication.dto.Request.product;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,

        @DecimalMin("0.0")
        BigDecimal price,

        Integer stock,
        Long categoryId) {
}

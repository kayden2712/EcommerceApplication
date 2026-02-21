package org.example.ecommerceapplication.product.dto;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        Integer stock,
        String color,
        String categoryName
) { }

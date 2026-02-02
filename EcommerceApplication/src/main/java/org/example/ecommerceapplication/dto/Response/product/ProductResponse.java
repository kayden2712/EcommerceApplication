package org.example.ecommerceapplication.dto.Response.product;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        Integer stock,
        String categoryName
) { }

package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Request.product.CreateProductRequest;
import org.example.ecommerceapplication.dto.Request.product.UpdateProductRequest;
import org.example.ecommerceapplication.dto.Response.product.ProductResponse;
import org.example.ecommerceapplication.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    void updateEntity(UpdateProductRequest request, @MappingTarget Product product);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);
}


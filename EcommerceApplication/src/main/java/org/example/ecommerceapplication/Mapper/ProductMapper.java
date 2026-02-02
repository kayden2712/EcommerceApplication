package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Request.product.CreateProductRequest;
import org.example.ecommerceapplication.dto.Response.product.ProductResponse;
import org.example.ecommerceapplication.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponses(List<Product> products);

}

package org.example.ecommerceapplication.product.service;

import org.example.ecommerceapplication.product.dto.CreateProductRequest;
import org.example.ecommerceapplication.product.dto.UpdateProductRequest;
import org.example.ecommerceapplication.product.dto.ProductResponse;

import java.util.List;


public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long productId, UpdateProductRequest request);

    void deleteProduct(Long productId);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long productId);

    void increaseProductStock(Long productId, int stock);

    void decreaseProductStock(Long productId, int stock);
}

package org.example.ecommerceapplication.service.product;

import org.example.ecommerceapplication.dto.Request.product.CreateProductRequest;
import org.example.ecommerceapplication.dto.Request.product.UpdateProductRequest;
import org.example.ecommerceapplication.dto.Response.product.ProductResponse;

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

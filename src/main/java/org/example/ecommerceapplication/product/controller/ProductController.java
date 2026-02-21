package org.example.ecommerceapplication.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.product.dto.CreateProductRequest;
import org.example.ecommerceapplication.product.dto.UpdateProductRequest;
import org.example.ecommerceapplication.product.dto.ProductResponse;
import org.example.ecommerceapplication.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return service.createProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PatchMapping("/{id}/increase-stock")
    public void increaseProductStock(@PathVariable Long id, @RequestParam int quantity) {
        service.increaseProductStock(id, quantity);
    }

    @PatchMapping("/{id}/decrease-stock")
    public void decreaseProductStock(@PathVariable Long id, @RequestParam int quantity) {
        service.decreaseProductStock(id, quantity);
    }


}

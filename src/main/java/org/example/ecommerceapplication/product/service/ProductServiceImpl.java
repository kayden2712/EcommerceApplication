package org.example.ecommerceapplication.product.service;

import java.util.List;

import org.example.ecommerceapplication.product.mapper.ProductMapper;
import org.example.ecommerceapplication.product.dto.CreateProductRequest;
import org.example.ecommerceapplication.product.dto.UpdateProductRequest;
import org.example.ecommerceapplication.product.dto.ProductResponse;
import org.example.ecommerceapplication.category.entity.Category;
import org.example.ecommerceapplication.product.entity.Product;
import org.example.ecommerceapplication.category.repository.CategoryRepository;
import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.exception.domain.InvalidOperationException;
import org.example.ecommerceapplication.common.exception.domain.ResourceNotFoundException;
import org.example.ecommerceapplication.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Category category = getCategoryEntityById(request.categoryId());
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        return productMapper.toResponse(repository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = getProductEntityById(productId);
        productMapper.updateEntity(request, product);
        Category category = getCategoryEntityById(request.categoryId());
        product.setCategory(category);
        return productMapper.toResponse(repository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = getProductEntityById(productId);
        repository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(productMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        return productMapper.toResponse(getProductEntityById(productId));
    }

    @Override
    public void increaseProductStock(Long productId, int stock) {
        Product product = getProductEntityById(productId);
        product.setStock(product.getStock() + stock);
    }

    @Override
    public void decreaseProductStock(Long productId, int stock) {
        Product product = getProductEntityById(productId);
        if (product.getStock() < stock) {
            throw new InvalidOperationException(ErrorCode.INSUFFICIENT_STOCK);
        }
        product.setStock(product.getStock() - stock);
    }

    private Category getCategoryEntityById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private Product getProductEntityById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}

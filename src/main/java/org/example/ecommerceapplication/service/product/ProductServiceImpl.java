package org.example.ecommerceapplication.service.product;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.Mapper.ProductMapper;
import org.example.ecommerceapplication.dto.Request.product.CreateProductRequest;
import org.example.ecommerceapplication.dto.Request.product.UpdateProductRequest;
import org.example.ecommerceapplication.dto.Response.product.ProductResponse;
import org.example.ecommerceapplication.entity.Category;
import org.example.ecommerceapplication.entity.Product;
import org.example.ecommerceapplication.repository.CategoryRepository;
import org.example.ecommerceapplication.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new IllegalStateException("Insufficient stock");
        }
        product.setStock(product.getStock() - stock);
    }

    private Category getCategoryEntityById(Long productId) {
        return categoryRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Category not found"));
    }

    private Product getProductEntityById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));
    }
}

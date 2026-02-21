package org.example.ecommerceapplication.category.service;

import org.example.ecommerceapplication.category.dto.CategoryRequest;
import org.example.ecommerceapplication.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void validateCategoryExists(Long categoryId);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);


}

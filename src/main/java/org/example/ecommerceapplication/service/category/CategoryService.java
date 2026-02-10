package org.example.ecommerceapplication.service.category;

import org.example.ecommerceapplication.dto.Request.category.CategoryRequest;
import org.example.ecommerceapplication.dto.Response.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void validateCategoryExists(Long categoryId);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);


}

package org.example.ecommerceapplication.category.service;

import java.util.List;

import org.example.ecommerceapplication.category.mapper.CategoryMapper;
import org.example.ecommerceapplication.category.dto.CategoryRequest;
import org.example.ecommerceapplication.category.dto.CategoryResponse;
import org.example.ecommerceapplication.category.entity.Category;
import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.domain.DuplicateResourceException;
import org.example.ecommerceapplication.exception.domain.ResourceNotFoundException;
import org.example.ecommerceapplication.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public void validateCategoryExists(Long categoryId) {
        if (!repository.existsById(categoryId)) {
            throw new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (repository.existsByName(request.name())) {
            throw new DuplicateResourceException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(repository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategoryEntityById(id);
        if (repository.existsByName(request.name())) {
            throw new DuplicateResourceException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        categoryMapper.updateEntity(request, category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public void delete(Long id) {
        Category category = getCategoryEntityById(id);
        repository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return repository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        return categoryMapper.toResponse(getCategoryEntityById(id));
    }

    private Category getCategoryEntityById(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}

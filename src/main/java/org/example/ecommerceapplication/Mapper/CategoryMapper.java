package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Request.category.CategoryRequest;
import org.example.ecommerceapplication.dto.Response.category.CategoryResponse;
import org.example.ecommerceapplication.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

    void updateEntity(CategoryRequest request, @MappingTarget Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);
}

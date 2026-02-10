package org.example.ecommerceapplication.controller.category;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.dto.Request.category.CategoryRequest;
import org.example.ecommerceapplication.dto.Response.category.CategoryResponse;
import org.example.ecommerceapplication.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return service.getAll();
    }

}

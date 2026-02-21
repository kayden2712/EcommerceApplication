package org.example.ecommerceapplication.category.repository;

import org.example.ecommerceapplication.category.entity.Category;
import org.example.ecommerceapplication.shared.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    boolean existsByName(String name);
}

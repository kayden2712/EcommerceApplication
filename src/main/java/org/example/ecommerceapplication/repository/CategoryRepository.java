package org.example.ecommerceapplication.repository;

import org.example.ecommerceapplication.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    boolean existsByName(String name);
}

package org.example.ecommerceapplication.repository;

import org.example.ecommerceapplication.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByName(String name);
}

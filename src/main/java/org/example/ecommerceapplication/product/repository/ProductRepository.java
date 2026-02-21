package org.example.ecommerceapplication.product.repository;

import org.example.ecommerceapplication.product.entity.Product;
import org.example.ecommerceapplication.shared.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByName(String name);
}

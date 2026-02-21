package org.example.ecommerceapplication.product.repository;

import org.example.ecommerceapplication.common.repository.BaseRepository;
import org.example.ecommerceapplication.product.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    boolean existsByName(String name);
}

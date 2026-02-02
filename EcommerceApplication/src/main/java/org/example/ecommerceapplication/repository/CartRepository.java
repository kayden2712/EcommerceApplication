package org.example.ecommerceapplication.repository;

import org.example.ecommerceapplication.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}

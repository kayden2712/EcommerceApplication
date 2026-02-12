package org.example.ecommerceapplication.repository;

import java.util.Optional;

import org.example.ecommerceapplication.entity.Cart;
import org.example.ecommerceapplication.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}

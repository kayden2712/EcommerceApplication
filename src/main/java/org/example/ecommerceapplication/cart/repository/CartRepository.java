package org.example.ecommerceapplication.cart.repository;

import java.util.Optional;

import org.example.ecommerceapplication.cart.entity.Cart;
import org.example.ecommerceapplication.common.repository.BaseRepository;
import org.example.ecommerceapplication.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}

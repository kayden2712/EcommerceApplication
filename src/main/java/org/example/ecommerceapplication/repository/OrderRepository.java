package org.example.ecommerceapplication.repository;

import org.example.ecommerceapplication.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}

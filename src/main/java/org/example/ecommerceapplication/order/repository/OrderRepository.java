package org.example.ecommerceapplication.order.repository;

import java.util.List;

import org.example.ecommerceapplication.common.enums.OrderStatus;
import org.example.ecommerceapplication.common.repository.BaseRepository;
import org.example.ecommerceapplication.order.entity.Order;
import org.example.ecommerceapplication.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findByUserAndStatus(User user, OrderStatus status, Pageable pageable);
}

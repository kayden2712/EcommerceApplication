package org.example.ecommerceapplication.service.order;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.Mapper.OrderMapper;
import org.example.ecommerceapplication.dto.Response.order.OrderResponse;
import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse getById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new IllegalStateException("Order not found"));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getByUser(Long id) {
        return repository.findById(id)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }
}

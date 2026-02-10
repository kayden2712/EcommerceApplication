package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderPricingServiceImpl implements OrderPricingService {

    @Override
    public BigDecimal calculateItemTotal(OrderItem orderItem) {
        return orderItem.getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
    }

    @Override
    public BigDecimal calculateOrderTotal(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(this::calculateItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

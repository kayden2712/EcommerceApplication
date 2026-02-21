package org.example.ecommerceapplication.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.example.ecommerceapplication.order.entity.OrderItem;

//Logic lien quan đến tiền
public interface OrderPricingService {
    BigDecimal calculateItemTotal(OrderItem orderItem);

    BigDecimal calculateOrderTotal(List<OrderItem> orderItems);
}

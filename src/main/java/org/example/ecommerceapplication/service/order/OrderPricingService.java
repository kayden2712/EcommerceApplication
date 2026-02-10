package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

//Logic lien quan đến tiền
public interface OrderPricingService {
    BigDecimal calculateItemTotal(OrderItem orderItem);

    BigDecimal calculateOrderTotal(List<OrderItem> orderItems);
}

package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.enums.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidatorImpl implements Validator {

    @Override
    public void validateCancel(Order order) {
        if (order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Order cannot be cancelled in its current status: " + order.getStatus());
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled.");
        }
    }
}

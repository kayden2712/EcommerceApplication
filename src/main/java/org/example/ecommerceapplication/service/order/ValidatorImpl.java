package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.entity.Order;
import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.enums.OrderStatus;
import org.example.ecommerceapplication.exception.domain.InvalidOperationException;
import org.springframework.stereotype.Service;

@Service
public class ValidatorImpl implements Validator {

    @Override
    public void validateCancel(Order order) {
        if (order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.SHIPPED) {
            throw new InvalidOperationException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOperationException(ErrorCode.ORDER_ALREADY_CANCELLED);
        }
    }
}

package org.example.ecommerceapplication.order.service;

import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.enums.OrderStatus;
import org.example.ecommerceapplication.common.exception.domain.InvalidOperationException;
import org.example.ecommerceapplication.order.entity.Order;
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

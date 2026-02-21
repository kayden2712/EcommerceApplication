package org.example.ecommerceapplication.order.service;

import org.example.ecommerceapplication.order.entity.Order;

//Chuyen trang thai don hang
public interface Validator {
    void validateCancel(Order order);
}

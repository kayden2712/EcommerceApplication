package org.example.ecommerceapplication.service.order;

import org.example.ecommerceapplication.entity.Order;

//Chuyen trang thai don hang
public interface Validator {
    void validateCancel(Order order);
}

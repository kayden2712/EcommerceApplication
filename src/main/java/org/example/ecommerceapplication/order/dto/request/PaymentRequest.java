package org.example.ecommerceapplication.order.dto.request;

import java.math.BigDecimal;

public record PaymentRequest(Long orderId, BigDecimal amount, String paymentMethod) {
}

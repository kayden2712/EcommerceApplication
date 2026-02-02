package org.example.ecommerceapplication.dto.Request.order;

import java.math.BigDecimal;

public record PaymentRequest(Long orderId, BigDecimal amount, String paymentMethod) {
}

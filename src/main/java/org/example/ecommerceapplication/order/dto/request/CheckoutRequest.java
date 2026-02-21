package org.example.ecommerceapplication.order.dto.request;

import java.math.BigDecimal;

public record CheckoutRequest(BigDecimal shippingFee, BigDecimal discount) {
}

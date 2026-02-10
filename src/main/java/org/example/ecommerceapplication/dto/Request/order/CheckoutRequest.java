package org.example.ecommerceapplication.dto.Request.order;

import java.math.BigDecimal;

public record CheckoutRequest(BigDecimal shippingFee, BigDecimal discount) {
}

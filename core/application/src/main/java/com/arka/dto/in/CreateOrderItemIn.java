package com.arka.dto.in;


import java.math.BigDecimal;

public record CreateOrderItemIn(
        Long productId,
        int quantity,
        BigDecimal unitPrice
) {
}

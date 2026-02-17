package com.arka.dto.in;

public record UpdateOrderItemIn(
        Long id,
        Long productId,
        int quantity
) {
}

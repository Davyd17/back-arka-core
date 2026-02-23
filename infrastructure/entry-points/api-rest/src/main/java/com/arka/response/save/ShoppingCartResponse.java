package com.arka.response.save;

import com.arka.enums.ShoppingCartStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ShoppingCartResponse(

        Long id,
        ShoppingCartStatus status,
        BigDecimal totalAmount,
        List<ShoppingCartItemResponse> items,
        Instant createdAt,
        Long userId
) {
}

package com.arka.dto.out;

import com.arka.enums.ShoppingCartStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CreateShoppingCartOut(

        Long id,
        ShoppingCartStatus status,
        BigDecimal totalAmount,
        List<ShoppingCartItemOut> items,
        Instant createdAt,
        Long userId
) {
}

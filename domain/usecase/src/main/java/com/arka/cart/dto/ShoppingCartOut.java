package com.arka.cart.dto;

import com.arka.enums.ShoppingCartStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ShoppingCartOut(

        Long id,
        String ownerEmail,
        ShoppingCartStatus status,
        BigDecimal totalAmount,
        List<ShoppingCartItemOut> items,
        Instant createdAt
) {
}

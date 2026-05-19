package com.arka.dto.in;

public record AddItemShoppingCartIn(

        Long userId,
        Long productId,
        int quantity
) {
}

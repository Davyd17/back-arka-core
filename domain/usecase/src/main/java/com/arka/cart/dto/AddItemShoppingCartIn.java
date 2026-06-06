package com.arka.cart.dto;

public record AddItemShoppingCartIn(

        Long userId,
        Long productId,
        int quantity
) {
}

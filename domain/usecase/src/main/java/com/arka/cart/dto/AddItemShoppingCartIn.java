package com.arka.cart.dto;

public record AddItemShoppingCartIn(
        Long productId,
        int quantity
) {
}

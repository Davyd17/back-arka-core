package com.arka.request;


public record ShoppingCartItemRequest(
        Long productId,
        int quantity
) {
}

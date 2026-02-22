package com.arka.request;


public record ShoppingCartItemRequest(
        Long product_id,
        int quantity
) {
}

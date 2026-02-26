package com.arka.dto.in;

public record CreateShoppingCartIn(

        Long userId,
        ShoppingCartItemIn item
) {
}

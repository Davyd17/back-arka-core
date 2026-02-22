package com.arka.dto.in;

import java.util.List;

public record CreateShoppingCartIn(

        Long userId,
        List<ShoppingCartItemIn> items
) {
}

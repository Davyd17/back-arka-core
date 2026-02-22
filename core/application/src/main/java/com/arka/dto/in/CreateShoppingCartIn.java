package com.arka.dto.in;

import java.util.List;

public record CreateShoppingCartIn(

        Long user_id,
        List<ShoppingCartItemIn> items
) {
}

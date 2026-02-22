package com.arka.request;

import com.arka.exceptions.Required;

import java.util.List;

public record CreateShoppingCartRequest(

        @Required(field = "user id")
        Long userId,

        @Required(field = "items")
        List<ShoppingCartItemRequest> items
) {
}

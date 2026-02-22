package com.arka.gateway.repository;

import com.arka.model.cart.ShoppingCart;

public interface ShoppingCartRepository {
    ShoppingCart save(ShoppingCart shoppingCart);
}

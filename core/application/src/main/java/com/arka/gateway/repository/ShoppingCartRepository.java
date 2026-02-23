package com.arka.gateway.repository;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.model.cart.ShoppingCart;

import java.util.List;

public interface ShoppingCartRepository {

    ShoppingCart save(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllAbandonedCarts();
}

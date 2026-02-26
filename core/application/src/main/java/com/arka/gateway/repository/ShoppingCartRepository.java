package com.arka.gateway.repository;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.model.cart.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {

    ShoppingCart save(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllAbandonedCarts();

    Optional<ShoppingCart> getActiveCartByUserId(Long userId);

    ShoppingCart update(ShoppingCart shoppingCart);
}

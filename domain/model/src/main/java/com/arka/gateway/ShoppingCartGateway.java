package com.arka.gateway;

import com.arka.model.cart.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartGateway {

    ShoppingCart save(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllAbandonedCarts();

    Optional<ShoppingCart> getLastCreatedCart(Long userId);
}

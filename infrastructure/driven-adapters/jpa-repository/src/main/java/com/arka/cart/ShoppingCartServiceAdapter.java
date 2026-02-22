package com.arka.cart;

import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.model.cart.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceAdapter implements ShoppingCartRepository {

    private final ShoppingCartJpaRepository repository;
    private final ShoppingCartEntityMapper mapper;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

        ShoppingCartEntity entity = mapper.toEntity(shoppingCart);
        return mapper.toDomain(repository.save(entity));
    }
}

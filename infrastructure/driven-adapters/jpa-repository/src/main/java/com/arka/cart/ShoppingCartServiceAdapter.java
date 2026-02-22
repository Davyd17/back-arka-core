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

        ShoppingCartEntity newCartEntity = mapper.toEntity(shoppingCart);

        newCartEntity.getItems().forEach(item -> {
            item.setShoppingCart(newCartEntity);
        });

        return mapper.toDomain(repository.save(newCartEntity));
    }
}

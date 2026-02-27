package com.arka.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.model.cart.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceAdapter implements ShoppingCartRepository {

    private final ShoppingCartJpaRepository repository;
    private final ShoppingCartEntityMapper mapper;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

        return saveOperation(shoppingCart);
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {

        return saveOperation(shoppingCart);
    }

    private ShoppingCart saveOperation(ShoppingCart shoppingCart) {

        ShoppingCartEntity newCartEntity = mapper.toEntity(shoppingCart);

        newCartEntity.getItems().forEach(item -> {
            item.setShoppingCart(newCartEntity);
        });

        return mapper.toDomain(repository.save(newCartEntity));
    }

    @Override
    public List<ShoppingCart> getAllAbandonedCarts() {

        return repository.findAllByStatus(ShoppingCartStatus.ABANDONED)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ShoppingCart> getActiveCartByUserId(Long userId) {
        return repository.getActiveCartByUserId(userId)
                .map(mapper::toDomain);
    }
}

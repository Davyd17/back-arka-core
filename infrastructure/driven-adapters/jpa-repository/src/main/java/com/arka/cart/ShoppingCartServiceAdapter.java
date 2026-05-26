package com.arka.cart;

import com.arka.entities.cart.ShoppingCart;
import com.arka.enums.ShoppingCartStatus;
import com.arka.cart.gateway.ShoppingCartGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceAdapter implements ShoppingCartGateway {

    private final ShoppingCartJpaRepository repository;
    private final ShoppingCartEntityMapper mapper;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

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
    public Optional<ShoppingCart> getLastCreatedCart(Long userId) {
        return repository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .map(mapper::toDomain);
    }
}

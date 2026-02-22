package com.arka.cart;

import com.arka.cart.item.ShoppingCartItemEntityMapper;
import com.arka.model.cart.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ShoppingCartItemEntityMapper.class)
public interface ShoppingCartEntityMapper {

    ShoppingCartEntity toEntity(ShoppingCart domain);

    ShoppingCart toDomain(ShoppingCartEntity entity);
}

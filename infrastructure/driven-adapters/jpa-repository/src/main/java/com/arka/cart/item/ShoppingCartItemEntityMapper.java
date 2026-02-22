package com.arka.cart.item;

import com.arka.model.cart.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemEntityMapper {

    @Mapping(target = "shoppingCart", ignore = true)
    ShoppingCartItemEntity toEntity(ShoppingCartItem domain);

    ShoppingCartItem toDomain(ShoppingCartItemEntity entity);
}

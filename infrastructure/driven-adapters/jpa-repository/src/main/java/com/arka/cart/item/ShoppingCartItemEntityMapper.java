package com.arka.cart.item;

import com.arka.entities.cart.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemEntityMapper {

    @Mappings({
            @Mapping(target = "shoppingCart", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    ShoppingCartItemEntity toEntity(ShoppingCartItem domain);

    ShoppingCartItem toDomain(ShoppingCartItemEntity entity);
}

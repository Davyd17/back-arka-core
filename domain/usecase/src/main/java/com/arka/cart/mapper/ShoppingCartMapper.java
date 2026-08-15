package com.arka.cart.mapper;

import com.arka.cart.dto.ShoppingCartOut;
import com.arka.entities.cart.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ShoppingCartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(target = "ownerEmail", source = "contact.email")
    ShoppingCartOut toOutDto(ShoppingCart domain);

}

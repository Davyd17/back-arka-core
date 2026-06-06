package com.arka.cart.mapper;

import com.arka.cart.dto.ShoppingCartOut;
import com.arka.entities.cart.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(uses = ShoppingCartItemMapper.class)
public interface ShoppingCartMapper {

    ShoppingCartOut toOutDto(ShoppingCart domain);

}

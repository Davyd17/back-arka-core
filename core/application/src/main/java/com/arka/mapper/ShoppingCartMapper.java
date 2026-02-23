package com.arka.mapper;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.model.cart.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(uses = ShoppingCartItemMapper.class)
public interface ShoppingCartMapper {

    ShoppingCartOut toOutDto(ShoppingCart domain);

}

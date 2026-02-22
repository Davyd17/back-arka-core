package com.arka.mapper;

import com.arka.dto.out.CreateShoppingCartOut;
import com.arka.model.cart.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ShoppingCartItemMapper.class)
public interface ShoppingCartMapper {

    CreateShoppingCartOut toCreateDto(ShoppingCart domain);

}

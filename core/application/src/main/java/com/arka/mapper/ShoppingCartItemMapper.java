package com.arka.mapper;

import com.arka.dto.out.ShoppingCartItemOut;
import com.arka.model.cart.ShoppingCartItem;
import org.mapstruct.Mapper;

@Mapper(uses = ProductMapper.class)
public interface ShoppingCartItemMapper {

    ShoppingCartItemOut toOutDTO(ShoppingCartItem domain);
}

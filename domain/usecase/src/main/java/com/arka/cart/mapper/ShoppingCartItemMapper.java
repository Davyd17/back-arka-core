package com.arka.cart.mapper;

import com.arka.entities.cart.ShoppingCartItem;
import com.arka.product.mapper.ProductMapper;
import com.arka.cart.dto.ShoppingCartItemOut;
import org.mapstruct.Mapper;

@Mapper(uses = ProductMapper.class)
public interface ShoppingCartItemMapper {

    ShoppingCartItemOut toOutDTO(ShoppingCartItem domain);
}

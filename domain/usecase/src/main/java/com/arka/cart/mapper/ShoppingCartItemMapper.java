package com.arka.cart.mapper;

import com.arka.product.mapper.ProductMapper;
import com.arka.cart.dto.ShoppingCartItemOut;
import com.arka.model.cart.ShoppingCartItem;
import org.mapstruct.Mapper;

@Mapper(uses = ProductMapper.class)
public interface ShoppingCartItemMapper {

    ShoppingCartItemOut toOutDTO(ShoppingCartItem domain);
}

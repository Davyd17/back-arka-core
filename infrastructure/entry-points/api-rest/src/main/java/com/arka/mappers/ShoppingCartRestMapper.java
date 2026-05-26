package com.arka.mappers;

import com.arka.cart.dto.ShoppingCartOut;
import com.arka.response.save.ShoppingCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ShoppingCartItemRestMapper.class)
public interface ShoppingCartRestMapper {

    ShoppingCartResponse toResponse(ShoppingCartOut outDTO);
}

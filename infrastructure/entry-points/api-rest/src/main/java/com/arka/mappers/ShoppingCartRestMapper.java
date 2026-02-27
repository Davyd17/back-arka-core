package com.arka.mappers;

import com.arka.dto.in.AddItemShoppingCartIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.request.AddItemShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ShoppingCartItemRestMapper.class)
public interface ShoppingCartRestMapper {

    ShoppingCartResponse toResponse(ShoppingCartOut outDTO);
}

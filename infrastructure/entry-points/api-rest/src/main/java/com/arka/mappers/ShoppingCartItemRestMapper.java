package com.arka.mappers;

import com.arka.cart.dto.ShoppingCartItemOut;
import com.arka.request.ShoppingCartItemRequest;
import com.arka.response.save.ShoppingCartItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ProductRestMapper.class)
public interface ShoppingCartItemRestMapper {

    ShoppingCartItemIn toInDto(ShoppingCartItemRequest request);

    ShoppingCartItemResponse toResponse(ShoppingCartItemOut outDTO);
}

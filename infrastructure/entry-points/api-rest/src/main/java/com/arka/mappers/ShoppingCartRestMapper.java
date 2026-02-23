package com.arka.mappers;

import com.arka.dto.in.CreateShoppingCartIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.request.CreateShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ShoppingCartItemRestMapper.class)
public interface ShoppingCartRestMapper {

    CreateShoppingCartIn toInDto(CreateShoppingCartRequest request);

    ShoppingCartResponse toResponse(ShoppingCartOut outDTO);
}

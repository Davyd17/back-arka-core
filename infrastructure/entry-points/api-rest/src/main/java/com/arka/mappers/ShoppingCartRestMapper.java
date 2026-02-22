package com.arka.mappers;

import com.arka.dto.in.CreateShoppingCartIn;
import com.arka.dto.out.CreateShoppingCartOut;
import com.arka.request.CreateShoppingCartRequest;
import com.arka.response.save.CreateShoppingCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = ShoppingCartItemRestMapper.class)
public interface ShoppingCartRestMapper {

    CreateShoppingCartIn toInDto(CreateShoppingCartRequest request);

    CreateShoppingCartResponse toResponse(CreateShoppingCartOut outDTO);
}

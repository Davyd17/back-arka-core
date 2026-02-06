package com.arka.mappers.request;

import com.arka.dto.in.CreateOrderIn;
import com.arka.request.CreateOrderItemRequest;
import com.arka.request.CreateOrderRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = CreateOrderItemRequestMapper.class)
public interface CreateOrderRequestMapper {

    CreateOrderIn toDomain(CreateOrderRequest response);
}

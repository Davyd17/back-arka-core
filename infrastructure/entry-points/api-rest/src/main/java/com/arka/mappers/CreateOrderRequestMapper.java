package com.arka.mappers;

import com.arka.dto.in.CreateOrderIn;
import com.arka.request.CreateOrderRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateOrderRequestMapper {

    CreateOrderIn toDomain(CreateOrderRequest response);
}

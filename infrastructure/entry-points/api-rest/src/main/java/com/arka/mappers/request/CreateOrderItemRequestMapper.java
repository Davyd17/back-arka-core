package com.arka.mappers.request;

import com.arka.dto.in.CreateOrderItemIn;
import com.arka.request.CreateOrderItemRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateOrderItemRequestMapper{

    CreateOrderItemRequest toRequest(CreateOrderItemIn in);

}

package com.arka.mappers.request;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.in.UpdateOrderIn;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderItemRequest;
import com.arka.request.UpdateOrderRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                CreateOrderItemRequestMapper.class,
                UpdateOrderItemRequest.class
        })
public interface OrderRequestMapper {

    CreateOrderIn toDomain(CreateOrderRequest request);

    UpdateOrderIn toDomain(UpdateOrderRequest request);
}

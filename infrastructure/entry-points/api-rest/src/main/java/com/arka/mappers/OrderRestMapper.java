package com.arka.mappers;

import com.arka.order.dto.CreateOrderIn;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.UpdateOrderIn;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderRequest;
import com.arka.response.save.CreateOrderResponse;
import com.arka.response.update.UpdateOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderRestMapper {

    CreateOrderIn toDomain(CreateOrderRequest request);

    UpdateOrderIn toDomain(UpdateOrderRequest request);

    CreateOrderResponse toResponse(CreateOrderOut output);

    UpdateOrderResponse toResponse(UpdateOrderOut output);
}

package com.arka.mappers;

import com.arka.order.dto.*;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderRequest;
import com.arka.response.save.CreateOrderResponse;
import com.arka.response.update.UpdateOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderRestMapper {

    CreateOrderIn toDomain(CreateOrderRequest request);

    UpdateOrderIn toDomain(UpdateOrderRequest request);

    CreateOrderResponse toResponse(CreateOrderOut output);

    UpdateOrderResponse toResponse(UpdateOrderOut output);

    @Mapping(target = "companyName", source = "contact.companyName")
    OrderEmailDataIn toEmailData(UpdateOrderOut output);
}

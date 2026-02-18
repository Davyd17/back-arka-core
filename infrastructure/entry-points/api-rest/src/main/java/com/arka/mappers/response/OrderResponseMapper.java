package com.arka.mappers.response;

import com.arka.dto.out.CompanyCreateOrderOut;
import com.arka.dto.out.CreateOrderItemOut;
import com.arka.dto.out.CreateOrderOut;
import com.arka.dto.out.UpdateOrderOut;
import com.arka.response.update.UpdateOrderResponse;
import com.arka.response.get.CompanyNameResponse;
import com.arka.response.save.CreateOrderItemResponse;
import com.arka.response.save.CreateOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    CreateOrderResponse toResponse(CreateOrderOut domain);

    UpdateOrderResponse toResponse(UpdateOrderOut domain);

    CreateOrderItemResponse toOrderItemResponse(CreateOrderItemOut domain);

    CompanyNameResponse toCompanyResponse(CompanyCreateOrderOut domain);
}

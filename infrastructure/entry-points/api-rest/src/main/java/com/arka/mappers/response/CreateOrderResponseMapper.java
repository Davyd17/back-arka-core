package com.arka.mappers.response;

import com.arka.dto.out.CompanyCreateOrderOut;
import com.arka.dto.out.CreateOrderItemOut;
import com.arka.dto.out.CreateOrderOut;
import com.arka.response.get.CompanyCreateOrderResponse;
import com.arka.response.save.CreateOrderItemResponse;
import com.arka.response.save.CreateOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateOrderResponseMapper {

    CreateOrderResponse toResponse(CreateOrderOut domain);

    CreateOrderItemResponse toOrderItemResponse(CreateOrderItemOut domain);

    CompanyCreateOrderResponse toCompanyResponse(CompanyCreateOrderOut domain);
}

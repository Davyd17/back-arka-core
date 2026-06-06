package com.arka.mappers;

import com.arka.shipping.dto.CreateShippingDetailIn;
import com.arka.shipping.dto.ShippingDetailOut;
import com.arka.request.CreateShippingDetailRequest;
import com.arka.response.save.CreateShippingDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingDetailRestMapper {

    CreateShippingDetailIn toDomain(CreateShippingDetailRequest request);

    CreateShippingDetailResponse toResponse(ShippingDetailOut outDTO);
}

package com.arka.mappers;

import com.arka.dto.in.CreateShippingDetailIn;
import com.arka.dto.out.ShippingDetailOut;
import com.arka.request.CreateShippingDetailRequest;
import com.arka.response.save.CreateShippingDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingDetailRestMapper {

    CreateShippingDetailIn toDomain(CreateShippingDetailRequest request);

    CreateShippingDetailResponse toResponse(ShippingDetailOut outDTO);
}

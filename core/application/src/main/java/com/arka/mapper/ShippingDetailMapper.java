package com.arka.mapper;

import com.arka.dto.out.ShippingDetailOut;
import com.arka.model.ShippingDetail;

import org.mapstruct.Mapper;


@Mapper(uses = OrderMapper.class)
public interface ShippingDetailMapper {

    ShippingDetailOut toOutDTO(ShippingDetail shippingDetailDomain);
}

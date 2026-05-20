package com.arka.shipping.mapper;

import com.arka.shipping.dto.ShippingDetailOut;
import com.arka.model.ShippingDetail;

import com.arka.order.mapper.OrderMapper;
import org.mapstruct.Mapper;


@Mapper(uses = OrderMapper.class)
public interface ShippingDetailMapper {

    ShippingDetailOut toOutDTO(ShippingDetail shippingDetailDomain);
}

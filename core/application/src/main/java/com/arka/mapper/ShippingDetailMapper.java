package com.arka.mapper;

import com.arka.dto.in.CreateShippingDetailIn;
import com.arka.dto.out.ShippingDetailOut;
import com.arka.model.ShippingDetail;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = OrderMapper.class)
public interface ShippingDetailMapper {

    ShippingDetailOut toOutDTO(ShippingDetail shippingDetailDomain);
}

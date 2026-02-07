package com.arka.shippingdetail;

import com.arka.information.address.AddressEntityMapper;
import com.arka.model.ShippingDetail;
import com.arka.order.OrderEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {
        OrderEntityMapper.class,
        AddressEntityMapper.class})
public interface ShippingDetailEntityMapper {

    @Mapping(target = "updatedAt", ignore = true)
    ShippingDetailEntity toEntity(ShippingDetail shippingDetailDomain);

    ShippingDetail toDomain(ShippingDetailEntity shippingDetailEntity);
}

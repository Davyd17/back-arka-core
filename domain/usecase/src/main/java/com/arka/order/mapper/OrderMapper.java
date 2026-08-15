package com.arka.order.mapper;

import com.arka.entities.order.Order;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.OrderSummaryOut;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "contact.companyName", source = "contact.company.name")
    CreateOrderOut toCreateOut(Order domain);

    OrderSummaryOut toSummaryDTO(Order domain);

    @Mapping(target = "contact.companyName", source = "contact.company.name")
    UpdateOrderOut toUpdateDTO(Order domain);
}

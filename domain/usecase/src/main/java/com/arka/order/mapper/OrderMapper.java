package com.arka.order.mapper;

import com.arka.entities.order.Order;
import com.arka.party.mapper.CompanyMapper;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.OrderSummaryOut;
import com.arka.order.dto.UpdateOrderOut;
import org.mapstruct.Mapper;

@Mapper(uses = CompanyMapper.class)
public interface OrderMapper {

    CreateOrderOut toCreateOut(Order domain);

    OrderSummaryOut toSummaryDTO(Order domain);

    UpdateOrderOut toUpdateDTO(Order domain);
}

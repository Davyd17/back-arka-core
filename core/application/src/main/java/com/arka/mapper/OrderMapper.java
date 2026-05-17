package com.arka.mapper;

import com.arka.dto.out.CreateOrderOut;
import com.arka.dto.out.OrderSummaryOut;
import com.arka.dto.out.UpdateOrderOut;
import com.arka.model.order.Order;
import org.mapstruct.Mapper;

@Mapper(uses = CompanyMapper.class)
public interface OrderMapper {

    CreateOrderOut toCreateOut(Order domain);

    OrderSummaryOut toSummaryDTO(Order domain);

    UpdateOrderOut toUpdateDTO(Order domain);
}

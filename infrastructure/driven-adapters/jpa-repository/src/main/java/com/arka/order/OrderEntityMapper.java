package com.arka.order;

import com.arka.company.CompanyEntityMapper;
import com.arka.model.order.Order;
import com.arka.order.item.OrderItemEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
        OrderItemEntityMapper.class,
        CompanyEntityMapper.class})
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order order);
}

package com.arka.order;

import com.arka.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order order);
}

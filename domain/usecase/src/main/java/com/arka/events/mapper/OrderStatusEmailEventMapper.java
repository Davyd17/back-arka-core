package com.arka.events.mapper;

import com.arka.notification.dto.OrderStatusEmailRequestedEvent;
import com.arka.notification.dto.OrderStatusEmailCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusEmailEventMapper {

    OrderStatusEmailCommand toCommand(OrderStatusEmailRequestedEvent event);
}

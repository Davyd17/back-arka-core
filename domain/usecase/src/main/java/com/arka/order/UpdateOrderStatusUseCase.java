package com.arka.order;

import com.arka.entities.order.Order;
import com.arka.enums.OrderStatus;
import com.arka.exceptions.InvalidTransitionStatusException;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {

    private final OrderService orderService;
    private final OrderGateway orderGateway;
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    public UpdateOrderOut execute(Long orderId, OrderStatus newStatus)
            throws InvalidTransitionStatusException {

        Order existingOrder = orderService.findById(orderId);
        existingOrder.updateStatus(newStatus);

        return mapper.toUpdateDTO(orderGateway.save(existingOrder));
    }
}

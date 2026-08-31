package com.arka.order;

import com.arka.entities.information.Contact;
import com.arka.entities.order.Order;
import com.arka.enums.OrderStatus;
import com.arka.exceptions.InvalidTransitionStatusException;
import com.arka.notification.dto.OrderStatusChangeRequestedEvent;
import com.arka.notification.gateway.OrderStatusEventPublisherGateway;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.MissingResourceException;

@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {

    private final OrderService orderService;
    private final OrderGateway orderGateway;
    private final OrderStatusEventPublisherGateway eventPublisherGateway;

    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    public UpdateOrderOut execute(Long orderId, OrderStatus newStatus)
            throws InvalidTransitionStatusException {

        Order existingOrder = orderService.findById(orderId);
        existingOrder.updateStatus(newStatus);

        eventPublisherGateway.publish(buildOrderStatusRequested(existingOrder));

        return mapper.toUpdateDTO(orderGateway.save(existingOrder));
    }

    private OrderStatusChangeRequestedEvent buildOrderStatusRequested(Order order){

        Contact owner = order.getContact();

        if (owner.getCompany() == null){
            throw new IllegalArgumentException(
                    "The owner of this request doesn't have a company association");
        }

        return new OrderStatusChangeRequestedEvent(
                order.getNumber(),
                order.getStatus(),
                owner.getCompany().getName(),
                owner.getEmail(),
                Instant.now()
        );
    }
}

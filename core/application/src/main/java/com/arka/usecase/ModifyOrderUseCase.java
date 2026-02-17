package com.arka.usecase;

import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.out.UpdateOrderOut;
import com.arka.exceptions.InvalidOrderStateException;
import com.arka.mapper.OrderItemMapper;
import com.arka.mapper.OrderItemMapperImpl;
import com.arka.mapper.OrderMapper;
import com.arka.mapper.OrderMapperImpl;
import com.arka.model.enums.OrderStatus;
import com.arka.model.order.Order;
import com.arka.model.order.OrderItem;
import com.arka.repository.order.OrderGateway;
import com.arka.service.OrderItemService;
import com.arka.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class ModifyOrderUseCase {

    private final OrderService orderService;
    private final OrderItemService itemService;

    private final OrderMapper orderMapper =
            new OrderMapperImpl();

    private final OrderItemMapper orderItemMapper =
            new OrderItemMapperImpl();

    private final OrderGateway orderGateway;

    public UpdateOrderOut execute(UpdateOrderIn request) {

        if(request == null)
            throw new IllegalArgumentException("Input cannot be null");

        Order order = orderService.findById(request.id());

        if(!order.getStatus().equals(OrderStatus.PENDING))
            throw new InvalidOrderStateException(order.getNumber(), order.getStatus());

        if(request.items() != null && !request.items().isEmpty()) {

            List<OrderItem> mappedRequestItems = request.items().stream()
                    .map(orderItemMapper::toDomain)
                    .toList();

            List<OrderItem> resolvedItems = itemService
                    .resolveProductsFromOrderItemsRequest(
                            mappedRequestItems, order.getType());

            order.updateItems(resolvedItems);

        } if (!request.notes().isBlank())
            order.updateNotes(request.notes());

        return orderMapper.toUpdateDTO(orderGateway.update(order));
    }

}

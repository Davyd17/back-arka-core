package com.arka.usecase;

import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.out.UpdateOrderOut;
import com.arka.mapper.OrderItemMapper;
import com.arka.mapper.OrderItemMapperImpl;
import com.arka.mapper.OrderMapper;
import com.arka.mapper.OrderMapperImpl;
import com.arka.model.order.Order;
import com.arka.model.order.OrderItem;
import com.arka.gateway.repository.order.OrderGateway;
import com.arka.service.OrderItemService;
import com.arka.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ModifyOrderUseCase {

    private final OrderService orderService;
    private final OrderItemService itemService;

    private final OrderMapper orderMapper =
            new OrderMapperImpl();

    private final OrderItemMapper orderItemMapper =
            new OrderItemMapperImpl();

    private final OrderGateway orderGateway;

    public UpdateOrderOut execute(UpdateOrderIn input) {

        if (input == null)
            throw new IllegalArgumentException("Order cannot be null");

        Order order = orderService.findById(input.id());

        if (input.items() != null && !input.items().isEmpty()) {

            List<OrderItem> mappedInputItems = input.items().stream()
                    .map(orderItemMapper::toDomain)
                    .toList();

            List<OrderItem> resolvedItems = itemService
                    .resolveProductsFromOrderItemsRequest(
                            mappedInputItems, order.getType());

            this.updateItems(order, resolvedItems);

        }

        if (!input.notes().isBlank())
            order.updateNotes(input.notes());

        return orderMapper.toUpdateDTO(orderGateway.update(order));
    }

    private void updateItems(Order order,
                             List<OrderItem> incomingItems) {

        this.itemsToRemove(order.getItems(), incomingItems)
                .forEach(order::removeItem);

        incomingItems.forEach(item -> syncItem(order, item));
    }


    private List<Long> itemsToRemove(List<OrderItem> currentItems,
                                     List<OrderItem> incomingItems) {

        Set<Long> incomingProductIds = incomingItems.stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toSet());

        return currentItems.stream()
                .map(item -> item.getProduct().getId())
                .filter(id -> !incomingProductIds.contains(id))
                .toList();
    }

    private void syncItem(Order order, OrderItem incomingItem) {

        Optional<OrderItem> existingItem = this.findMatchingItem
                        (order.getItems(), incomingItem);

        if (existingItem.isPresent()) {

            OrderItem item = existingItem.get();

            if (item.getQuantity() != incomingItem.getQuantity())
                order.changeItemQuantity
                        (item.getProduct().getId(), incomingItem.getQuantity());

        } else
            order.addItem(incomingItem);

    }


    private Optional<OrderItem> findMatchingItem(List<OrderItem> currentItems,
                                                 OrderItem incomingItem) {

        Long incomingProductId = incomingItem.getProduct().getId();

        return currentItems.stream()
                .filter(item ->
                        incomingProductId.equals(item.getProduct().getId()))
                .findFirst();
    }
}

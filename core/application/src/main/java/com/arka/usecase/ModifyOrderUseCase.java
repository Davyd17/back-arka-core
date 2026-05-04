package com.arka.usecase;

import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.out.UpdateOrderOut;
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

    private final OrderGateway orderGateway;

    public UpdateOrderOut execute(UpdateOrderIn input) {

        if (input == null)
            throw new IllegalArgumentException("Order cannot be null");

        Order existingOrder = orderService.findById(input.id());

        if(hasItems(input))
            updateItems(existingOrder, input.items());

        if (hasNotes(input))
            existingOrder.updateNotes(input.notes());

        return orderMapper.toUpdateDTO(orderGateway.update(existingOrder));
    }

    private boolean hasItems(UpdateOrderIn input){
        return input.items() != null && !input.items().isEmpty();
    }

    private boolean hasNotes(UpdateOrderIn input){
        return input.notes() != null && !input.notes().isBlank();
    }

    private void updateItems(Order order, Set<UpdateOrderIn.Item> incomingItems){

        itemsToRemove(order.getItems(), incomingItems)
                .forEach(order::removeItem);

        incomingItems.forEach(item ->
                syncItem(order, itemService.resolveItem(
                        order.getType(),
                        item.productId(),
                        item.quantity())));
    }

    private List<Long> itemsToRemove(List<OrderItem> currentItems,
                                     Set<UpdateOrderIn.Item> incomingItems) {

        Set<Long> incomingProductIds = incomingItems.stream()
                .map(UpdateOrderIn.Item::productId)
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

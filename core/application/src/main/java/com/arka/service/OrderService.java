package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.order.OrderGateway;
import com.arka.model.order.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private final OrderGateway orderGateway;

    public Order findById(Long id){

        return orderGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Order with id %s not found", id)
                ));

    }
}

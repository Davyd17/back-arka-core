package com.arka.gateway.order;

import com.arka.model.order.Order;

import java.util.Optional;

public interface OrderGateway {

    Order createOrder(Order newOrder);

    Optional<Order> findById(Long id);
}

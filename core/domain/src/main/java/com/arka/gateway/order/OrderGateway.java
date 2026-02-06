package com.arka.gateway.order;

import com.arka.model.order.Order;

public interface OrderGateway {

    Order createOrder(Order newOrder);
}

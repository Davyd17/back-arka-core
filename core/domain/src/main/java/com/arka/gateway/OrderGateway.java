package com.arka.gateway;

import com.arka.model.Order;

public interface OrderGateway {

    Order createOrder(Order newOrder);
}

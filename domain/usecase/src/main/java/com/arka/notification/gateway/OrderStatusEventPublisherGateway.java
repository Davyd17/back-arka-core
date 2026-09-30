package com.arka.notification.gateway;

import com.arka.notification.dto.OrderStatusEmailRequestedEvent;

public interface OrderStatusEventPublisherGateway {

    void publish(OrderStatusEmailRequestedEvent event);
}

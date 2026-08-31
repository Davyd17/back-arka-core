package com.arka.notification.gateway;

import com.arka.notification.dto.OrderStatusChangeRequestedEvent;

public interface OrderStatusEventPublisherGateway {

    void publish(OrderStatusChangeRequestedEvent event);
}

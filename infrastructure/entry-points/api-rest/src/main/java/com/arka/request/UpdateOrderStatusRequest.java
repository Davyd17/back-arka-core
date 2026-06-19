package com.arka.request;

import com.arka.enums.OrderStatus;
import com.arka.exceptions.Required;

public record UpdateOrderStatusRequest(
        @Required(field = "OrderStatus") OrderStatus status
) {
}

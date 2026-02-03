package com.arka.request;

import com.arka.exceptions.Required;
import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateOrderRequest(

        @Required(field = "number")
        String number,

        @NotNull(message = "Order status is required")
        OrderStatus status,

        String notes,

        @NotNull(message = "Order type is required")
        OrderType type,

        @Required(field = "company id")
        CompanyIdRequest companyId
) {
}

package com.arka.exceptions;

import com.arka.enums.OrderStatus;

public class InvalidOrderStateException extends RuntimeException {

    public InvalidOrderStateException(String orderNumber, OrderStatus currentState) {
        super(String.format(
                "Order [%s] cannot be modified because it is in state [%s]. Only PENDING orders can be updated.",
                orderNumber, currentState));
    }
}

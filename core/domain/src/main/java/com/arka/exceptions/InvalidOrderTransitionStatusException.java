package com.arka.exceptions;

import com.arka.enums.OrderStatus;

public class InvalidOrderTransitionStatusException extends RuntimeException {

    public InvalidOrderTransitionStatusException(OrderStatus currentStatus,
                                                 OrderStatus nextStatus) {
        super(String.format
                ("Cannot transition from [%s] to [%s]", currentStatus, nextStatus));
    }

    public InvalidOrderTransitionStatusException(OrderStatus currentStatus){

        super(String.format
                ("Cannot transition from a [%s] order", currentStatus));
    }
}

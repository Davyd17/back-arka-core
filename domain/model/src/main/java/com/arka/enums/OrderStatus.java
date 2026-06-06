package com.arka.enums;

import com.arka.entities.order.Order;
import com.arka.exceptions.InvalidEditableStatusException;
import com.arka.exceptions.InvalidTransitionStatusException;

public enum OrderStatus {

    PENDING {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {

            if(next == PROCESSING)
                return next;

            throw new InvalidTransitionStatusException(PENDING, next);
        }
    },

    PROCESSING {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {

            if (next == AUTHORIZED || next == CANCELLED)
                return next;

            throw new InvalidTransitionStatusException(PROCESSING, next);
        }
    },

    AUTHORIZED {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {
            throw new InvalidTransitionStatusException(AUTHORIZED);
        }
    },

    CANCELLED {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {
            throw new InvalidTransitionStatusException(CANCELLED);
        }
    };

    public abstract OrderStatus transitionTo(OrderStatus next);

    public void validateEditable(String orderNumber){
        if(this != PENDING) {
            throw new InvalidEditableStatusException(
                    Order.class,
                    "number",
                    orderNumber,
                    this);
        }
    }
}

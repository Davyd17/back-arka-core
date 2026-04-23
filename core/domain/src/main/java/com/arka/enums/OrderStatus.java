package com.arka.enums;

import com.arka.exceptions.InvalidOrderStatusException;
import com.arka.exceptions.InvalidOrderTransitionStatusException;

public enum OrderStatus {

    PENDING {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {

            if(next == PROCESSING)
                return next;

            throw new InvalidOrderTransitionStatusException(PENDING, next);
        }
    },

    PROCESSING {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {

            if (next == AUTHORIZED || next == CANCELLED)
                return next;

            throw new InvalidOrderTransitionStatusException(PROCESSING, next);
        }
    },

    AUTHORIZED {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {
            throw new InvalidOrderTransitionStatusException(AUTHORIZED);
        }
    },

    CANCELLED {
        @Override
        public OrderStatus transitionTo(OrderStatus next) {
            throw new InvalidOrderTransitionStatusException(CANCELLED);
        }
    };

    public abstract OrderStatus transitionTo(OrderStatus next);

    public void validateEditable(String orderNumber){
        if(this != PENDING) {
            throw new InvalidOrderStatusException(orderNumber, this);
        }
    }
}

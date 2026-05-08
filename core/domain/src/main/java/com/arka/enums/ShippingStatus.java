package com.arka.enums;

import com.arka.exceptions.InvalidTransitionStatusException;

public enum ShippingStatus {

    PENDING {
        @Override
        public ShippingStatus transitionTo(ShippingStatus next){
            if(next == IN_DISPATCH)
                return next;

            throw new InvalidTransitionStatusException(PENDING, next);
        }
    },

    IN_DISPATCH {
        @Override
        public ShippingStatus transitionTo(ShippingStatus next){
            if(next == SENT)
                return next;

            throw new InvalidTransitionStatusException(IN_DISPATCH, next);
        }
    },

    SENT {
        @Override
        public ShippingStatus transitionTo(ShippingStatus next){
            if(next == DELIVERED)
                return next;

            throw new InvalidTransitionStatusException(SENT, next);
        }
    },

    DELIVERED {
        @Override
        public ShippingStatus transitionTo(ShippingStatus next){
            if(next == RECEIVED)
                return next;

            throw new InvalidTransitionStatusException(DELIVERED, next);
        }
    },

    RECEIVED {
        @Override
        public ShippingStatus transitionTo(ShippingStatus next){
            throw new InvalidTransitionStatusException(RECEIVED);
        }
    };

    public abstract ShippingStatus transitionTo(ShippingStatus next);
}

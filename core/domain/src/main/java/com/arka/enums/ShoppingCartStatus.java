package com.arka.enums;

import com.arka.exceptions.InvalidEditableStatusException;
import com.arka.exceptions.InvalidTransitionStatusException;
import com.arka.model.cart.ShoppingCart;

public enum ShoppingCartStatus {

    ACTIVE {
        @Override
        public ShoppingCartStatus transitionTo(ShoppingCartStatus next) {
            return next;
        }
    },

    PROCESSED {
        @Override
        public ShoppingCartStatus transitionTo(ShoppingCartStatus next) {
            throw new InvalidTransitionStatusException(PROCESSED);
        }
    },

    CANCELLED {
        @Override
        public ShoppingCartStatus transitionTo(ShoppingCartStatus next) {
            throw new InvalidTransitionStatusException(CANCELLED);
        }
    },

    ABANDONED {
        @Override
        public ShoppingCartStatus transitionTo(ShoppingCartStatus next) {

            if(next == ACTIVE || next == CANCELLED)
                return next;
            throw new InvalidTransitionStatusException(ABANDONED, next);
        }
    };

    public abstract ShoppingCartStatus transitionTo(ShoppingCartStatus next);

    public void validateEditable(Long id) {
        if (this == PROCESSED || this == CANCELLED) {
            throw new InvalidEditableStatusException(
                    ShoppingCart.class,
                    "Id",
                    id,
                    this);
        }
    }
}

package com.arka.exceptions;

public class InvalidTransitionStatusException extends RuntimeException {

    public InvalidTransitionStatusException(Enum<?> currentStatus,
                                            Enum<?> nextStatus) {
        super(String.format
                ("Cannot transition from [%s] to [%s]", currentStatus, nextStatus));
    }

    public InvalidTransitionStatusException(Enum<?> currentStatus){

        super(String.format
                ("Cannot transition from a [%s] status", currentStatus));
    }
}

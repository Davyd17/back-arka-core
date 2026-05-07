package com.arka.exceptions;

/**
 * Thrown when an operation is attempted on an entity
 * that is in a state which does not allow modification.
 *
 * <p>For example, attempting to modify an order that is no longer PENDING.
 */
public class InvalidEditableStatusException extends RuntimeException {

    /**
     * @param reference           the entity class (e.g. {@code Order.class})
     * @param identifierReference the name of the identifying field (e.g. {@code "number"})
     * @param identifier          the identifying value (e.g. the order number)
     * @param currentState        the current state that prevents the operation
     */
    public InvalidEditableStatusException(Class<?> reference,
                                          String identifierReference,
                                          Object identifier,
                                          Enum<?> currentState) {
        super(String.format(
                "%s with %s [%s] cannot be modified because it is in state [%s].",
                reference.getSimpleName(), identifierReference, identifier, currentState));
    }
}

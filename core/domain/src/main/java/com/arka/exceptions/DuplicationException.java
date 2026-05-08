package com.arka.exceptions;

/**
 * Thrown when two values that must be distinct are found to be equal.
 *
 * <p>For example, a warehouse transfer where the source and destination
 * warehouse are the same.
 */
public class DuplicationException extends RuntimeException {

    /**
     * @param reference the entity or context where the duplication occurred (e.g. {@code Warehouse.class})
     * @param value1    the first value
     * @param value2    the second value that must differ from {@code value1}
     */
    public DuplicationException(Class<?> reference, Object value1, Object value2) {
        super(
                String.format("[%s] [%s] cannot be the same as [%s]",
                        reference.getSimpleName(), value1, value2));
    }
}

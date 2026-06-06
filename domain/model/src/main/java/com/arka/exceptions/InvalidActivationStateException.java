package com.arka.exceptions;
/**
 * Thrown when an attempt is made to change the activation state
 * of an entity that is already in the requested state.
 *
 * <p>For example, activating an already active product
 * or deactivating an already inactive one.
 */
public class InvalidActivationStateException extends RuntimeException {


    /**
     * @param reference the entity class (e.g. {@code Product.class}, {@code Company.class})
     * @param attribute the identifying value (e.g. SKU, ID)
     * @param state     current activation state — {@code true} for active, {@code false} for inactive
     *
     * <p>Example usage:
     * <pre>
     *     if (this.active)
     *         throw new InvalidActivationStateException("Product", this.sku, this.active);
     * </pre>
     */
    public InvalidActivationStateException(Class<?> reference, Object attribute, boolean state) {
        super(
                String.format("[%s] with [%s] is already %s",
                        reference.getSimpleName(),
                        attribute,
                        state ? "active" : "inactive")
        );
    }
}

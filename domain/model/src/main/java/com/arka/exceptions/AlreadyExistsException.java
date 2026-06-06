package com.arka.exceptions;

/**
 * Thrown when an attempt is made to create or add an entity
 * that already exists in the context.
 *
 * <p>For example, adding a contact that is already associated
 * with a company, or adding a duplicate product category.
 */
public class AlreadyExistsException extends RuntimeException {

    /**
     * @param reference the entity class (e.g. {@code Contact.class}, {@code ProductCategory.class})
     * @param id        the identifier of the already existing entity
     */
    public AlreadyExistsException(Class<?> reference, Long id) {
        super(String.format("[%s] with id [%s] already exists",
                reference.getSimpleName(), id));
    }
}

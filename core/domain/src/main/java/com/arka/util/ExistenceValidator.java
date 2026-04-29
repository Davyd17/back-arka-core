package com.arka.util;

import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;

import java.util.Collection;
import java.util.function.Function;

/**
 * Utility class for validating entity existence within domain collections.
 *
 * <p>Provides reusable existence checks to avoid duplication across domain models
 * when managing collections of entities.
 */
public class ExistenceValidator {

    private ExistenceValidator(){}

    /**
     * Validates that an entity with the given id does not already exist in the collection.
     *
     * @param collection  the collection to search in
     * @param idExtractor function to extract the id from each element
     * @param id          the id to check for
     * @param entityClass the entity class (e.g. {@code Contact.class})
     * @throws AlreadyExistsException if an entity with the given id already exists
     */
    public static <T> void validateNoDuplicate(
            Collection<T> collection,
            Function<T, Long> idExtractor,
            Long id,
            Class<T> entityClass
    ){

        boolean alreadyExists = collection.stream()
                .anyMatch(item ->
                        idExtractor.apply(item).equals(id));

        if (alreadyExists){
            throw new AlreadyExistsException(entityClass, id);
        }
    }

    /**
     * Validates that an entity with the given id exists in the collection.
     *
     * @param collection  the collection to search in
     * @param idExtractor function to extract the id from each element
     * @param id          the id to check for
     * @param entityClass the entity class (e.g. {@code Contact.class})
     * @throws NotFoundException if no entity with the given id is found
     */
    public static <T> void validateExists(
            Collection<T> collection,
            Function<T, Long> idExtractor,
            Long id,
            Class<T> entityClass
    ){

        boolean exists = collection.stream()
                .anyMatch(item -> idExtractor.apply(item).equals(id));

        if(!exists)
            throw new NotFoundException(
                    String.format("[%s] with id [%s] not found",
                            entityClass.getSimpleName(), id));
    }
}

package com.arka.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String reference, Long id) {
        super(String.format("[%s] with id [%s] already exists", reference, id));
    }
}

package com.arka.exceptions;

public class MissingRequiredFileException extends RuntimeException {
    public MissingRequiredFileException(String message) {
        super(message);
    }
}

package com.arka.exceptions;

public class InvalidProductStateException extends RuntimeException {

    public InvalidProductStateException(String sku, boolean state) {
        super(
                String.format("Product [%s] is already %s",
                        sku, state ? "active" : "inactive")
        );
    }
}

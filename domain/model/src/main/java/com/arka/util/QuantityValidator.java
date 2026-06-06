package com.arka.util;

public class QuantityValidator {

    public static void validate(int quantity){
        if(quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than 0");
    }
}

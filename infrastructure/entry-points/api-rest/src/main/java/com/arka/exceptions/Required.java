package com.arka.exceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation to avoid to make the same message
 * manually each time we use @NotBlank.
 * Example: "Missing required field: {field}"
 * @code @Required(field = "name")
 */

@NotBlank
@Constraint(validatedBy = {})
@Target({ FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface Required {

    String field();

    String message() default "Missing required field: {field}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

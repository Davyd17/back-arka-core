package com.arka.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.List;

public record CreateContactRequest(

        @NotBlank(message
                = "Missing name")
        String name,

        @NotBlank(message =
                "Missing last name")
        String lastName,

        @NotBlank(message =
                "Missing job position")
        String position,


        @NotBlank(message = "Missing email")
        @Email(message = "Enter a valid email")
        String email,

        @NotEmpty(message =
                "There must be at least one address")
        List<CreateAddressRequest> addresses,

        @NotEmpty(message =
                "There must be at least one address")
        List<CreatePhoneNumberRequest> phoneNumbers,

        Long userId
) {
}

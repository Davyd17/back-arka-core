package com.arka.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CreatePhoneNumberRequest (

        @NotBlank(message = "Country code required")
        String countryCode,

        @Nullable
        String extension,

        @NotBlank(message = "Phone required")
        String phone
){
}

package com.arka.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;


public record CreateAddressRequest (

        @NotBlank(message = "Missing country")
        String country,

        @NotBlank(message = "Missing city")
        String city,

        @Nullable
        String zipCode,

        @NotBlank(message = "Missing address")
        String address,

        @Nullable
        String notes
)
{

}

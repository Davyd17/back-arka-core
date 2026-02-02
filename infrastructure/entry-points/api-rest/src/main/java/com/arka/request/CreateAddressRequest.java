package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;


public record CreateAddressRequest (

        @Required(field = "Country")
        String country,

        @Required(field = "City")
        String city,

        @Nullable
        String zipCode,

        @Required(field = "Address")
        String address,

        @Nullable
        String notes
)
{

}

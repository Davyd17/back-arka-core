package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.annotation.Nullable;

public record CreatePhoneNumberRequest (

        @Required(field = "country code")
        String countryCode,

        @Nullable
        String extension,

        @Required(field = "phone")
        String phone
){
}

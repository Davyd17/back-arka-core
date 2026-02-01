package com.arka.response.save;

public record PhoneNumberSaveResponse(
        String countryCode,
        String extension,
        String phone
) {
}

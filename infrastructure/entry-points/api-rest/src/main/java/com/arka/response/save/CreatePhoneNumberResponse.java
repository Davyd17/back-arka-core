package com.arka.response.save;

public record CreatePhoneNumberResponse(
        String countryCode,
        String extension,
        String phone
) {
}

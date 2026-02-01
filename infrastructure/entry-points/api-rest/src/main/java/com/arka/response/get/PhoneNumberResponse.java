package com.arka.response.get;

import java.time.Instant;

public record PhoneNumberResponse(
        String countryCode,
        String extension,
        String phone,
        boolean active,
        Instant createdAt
) {
}

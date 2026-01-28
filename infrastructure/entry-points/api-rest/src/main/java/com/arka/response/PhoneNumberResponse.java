package com.arka.response;

import java.time.Instant;

public record PhoneNumberResponse(
        Long id,
        String countryCode,
        String extension,
        String phone,
        boolean isActive,
        Instant created_at,
        Instant updated_at
) {
}

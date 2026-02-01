package com.arka.response.get;

import java.time.Instant;
import java.util.List;

public record ContactResponse(
        Long contactId,
        String name,
        String lastName,
        String position,
        String email,
        List<AddressResponse> addresses,
        List<PhoneNumberResponse> phoneNumbers,
        Instant createdAt,
        boolean active,
        Long userId
) {

}

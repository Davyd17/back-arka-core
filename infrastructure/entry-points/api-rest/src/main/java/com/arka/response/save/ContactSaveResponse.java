package com.arka.response.save;

import java.util.List;

public record ContactSaveResponse(
        String name,
        String lastName,
        String position,
        String email,
        List<CreateAddressResponse> addresses,
        List<CreatePhoneNumberResponse> phoneNumbers,
        Long userId
) {
}

package com.arka.response.save;

import java.util.List;

public record ContactSaveResponse(
        String name,
        String lastName,
        String position,
        String email,
        List<AddressSaveResponse> addresses,
        List<PhoneNumberSaveResponse> phoneNumbers,
        Long userId
) {
}

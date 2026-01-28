package com.arka.response;

import com.arka.entities.enums.AddressType;

public record AddressResponse(
        Long id,
        String country,
        String city,
        String zipCode,
        String address,
        String notes,
        AddressType type,
        boolean isActive
) {
}

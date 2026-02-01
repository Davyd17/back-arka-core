package com.arka.response.get;

import com.arka.model.enums.AddressType;

public record AddressResponse(
        String country,
        String city,
        String zipCode,
        String address,
        String notes,
        AddressType type,
        boolean active
) {
}

package com.arka.response.save;

public record AddressSaveResponse(
        String country,
        String city,
        String zipCode,
        String address,
        String notes
) {
}

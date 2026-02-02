package com.arka.response.save;

public record CreateAddressResponse(
        String country,
        String city,
        String zipCode,
        String address,
        String notes
) {
}

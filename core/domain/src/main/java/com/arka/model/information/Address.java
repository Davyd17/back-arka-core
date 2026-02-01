package com.arka.model.information;

import com.arka.model.enums.AddressType;
import lombok.*;

@Builder(toBuilder = true)
public record Address(
        String country,
        String city,
        String zipCode,
        String address,
        String notes,
        AddressType type,
        boolean active)
{

    public static Address createSupplierAddress(Address address) {
        return address.toBuilder()
                .active(true)
                .type(AddressType.SUPPLIER)
                .build();
    }
}

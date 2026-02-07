package com.arka.dto.in;

import com.arka.model.enums.ShippingStatus;

public record CreateShippingDetailIn(

        String carrier,
        String trackingNumber,
        String notes,
        ShippingStatus status,
        Long orderId,
        Long originAddressId,
        Long destinationAddressId
) {
}

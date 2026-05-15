package com.arka.dto.in;

public record CreateShippingDetailIn(

        String carrier,
        String trackingNumber,
        String notes,
        Long orderId,
        Long originAddressId,
        Long destinationAddressId
) {
}

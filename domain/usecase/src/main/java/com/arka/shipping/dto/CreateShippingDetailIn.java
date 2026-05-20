package com.arka.shipping.dto;

public record CreateShippingDetailIn(

        String carrier,
        String trackingNumber,
        String notes,
        Long orderId,
        Long originAddressId,
        Long destinationAddressId
) {
}

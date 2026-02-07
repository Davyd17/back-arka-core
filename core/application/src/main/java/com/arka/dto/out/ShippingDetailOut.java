package com.arka.dto.out;

import com.arka.model.enums.ShippingStatus;
import com.arka.model.information.Address;

import java.time.Instant;

public record ShippingDetailOut(
        Long id,
        String carrier,
        String trackingNumber,
        String notes,
        ShippingStatus status,
        Instant createdAt,
        OrderSummaryOut order,
        Address originAddress,
        Address destinationAddress
) {
}

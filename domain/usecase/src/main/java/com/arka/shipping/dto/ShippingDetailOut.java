package com.arka.shipping.dto;

import com.arka.order.dto.OrderSummaryOut;
import com.arka.enums.ShippingStatus;
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
        Address origin,
        Address destination
) {
}

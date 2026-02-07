package com.arka.response.save;

import com.arka.response.get.AddressResponse;

public record CreateShippingDetailResponse(

        Long id,
        String carrier,
        String trackingNumber,
        String notes,
        String status,
        String createdAt,
        OrderSummaryResponse order,
        AddressResponse originAddress,
        AddressResponse destinationAddress
) {
}

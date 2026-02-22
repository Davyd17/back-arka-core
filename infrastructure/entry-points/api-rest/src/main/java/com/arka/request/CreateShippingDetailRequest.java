package com.arka.request;

import com.arka.exceptions.Required;
import com.arka.enums.ShippingStatus;

public record CreateShippingDetailRequest(

        @Required(field = "carrier")
        String carrier,

        @Required(field = "trackingNumber")
        String trackingNumber,

        String notes,

        @Required(field = "status")
        ShippingStatus status,

        @Required(field = "id")
        Long orderId,

        @Required(field = "originAddressId")
        Long originAddressId,

        @Required(field = "destinationAddressId")
        Long destinationAddressId

) {
}

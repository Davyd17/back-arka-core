package com.arka.request;

import com.arka.exceptions.Required;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateShippingDetailRequest(

        @Schema(description = "Shipping carrier name", example = "DHL Express")
        @Required(field = "carrier")
        String carrier,

        @Schema(description = "Unique carrier tracking number", example = "TRK100293847")
        @Required(field = "trackingNumber")
        String trackingNumber,

        @Schema(description = "Special instructions or notes for delivery",
                example = "Leave package at front desk", nullable = true)
        String notes,

        @Schema(description = "ID of the associated order", example = "1050")
        @Required(field = "orderId")
        Long orderId,

        @Schema(description = "ID of the origin address/warehouse", example = "1")
        @Required(field = "originAddressId")
        Long originAddressId,

        @Schema(description = "ID of the destination address", example = "45")
        @Required(field = "destinationAddressId")
        Long destinationAddressId) {
}

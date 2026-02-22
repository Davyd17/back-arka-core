package com.arka.model;

import com.arka.enums.ShippingStatus;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder(toBuilder = true)
public class ShippingDetail {
    private Long id;
    private String carrier;
    private String trackingNumber;
    private String notes;
    private ShippingStatus status;
    private Instant createdAt;
    private Order order;
    private Address originAddress;
    private Address destinationAddress;
}

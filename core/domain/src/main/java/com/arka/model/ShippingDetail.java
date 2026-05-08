package com.arka.model;

import com.arka.enums.ShippingStatus;
import com.arka.exceptions.DuplicationException;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import com.arka.util.NullValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ShippingDetail {
    private Long id;
    private String carrier;
    private String trackingNumber;
    private String notes;
    private ShippingStatus status;
    private Instant createdAt;
    private Order order;
    private Address origin;
    private Address destination;

    public static ShippingDetail create(
            String carrier,
            String trackingNumber,
            Order order,
            Address originAddress,
            Address destinationAddress
    ){

        validateAddressDuplication(originAddress, destinationAddress);

        return ShippingDetail.builder()
                .carrier(carrier)
                .trackingNumber(trackingNumber)
                .status(ShippingStatus.PENDING)
                .order(order)
                .origin(originAddress)
                .destination(destinationAddress)
                .build();
    }

    public void updateStatus(ShippingStatus status){

        NullValidator.validate(status, "status");
        this.status = this.status.transitionTo(status);
    }

    public void updateNotes(String notes){
        this.notes = notes;
    }

    private static void validateAddressDuplication(Address origin, Address destination){

        if (origin.getId().equals(destination.getId()))
            throw new DuplicationException(
                    Address.class,
                    "origin",
                    "destination");
    }
}

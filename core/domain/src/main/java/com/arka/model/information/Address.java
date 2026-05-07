package com.arka.model.information;

import com.arka.enums.AddressType;
import com.arka.exceptions.InvalidActivationStateException;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Address {

    private Long id;
    private String country;
    private String city;
    @Setter private String zipCode;
    private String address;
    @Setter private String notes;
    private AddressType type;
    private boolean active;

    public static Address create(
            String country,
            String city,
            @Nullable String zipCode,
            AddressType type,
            String address
    ) {
        return Address.builder()
                .country(country)
                .city(city)
                .zipCode(zipCode)
                .address(address)
                .type(type)
                .active(true)
                .build();
    }

    public void activate(){

        if(this.active)
            throw new InvalidActivationStateException
                    (this.getClass(), this.id, this.active);

        this.active = true;
    }

    public void deactivate(){

        if(!this.active)
            throw new InvalidActivationStateException
                    (this.getClass(), this.id, this.active);

        this.active = false;
    }

    public void changeType(AddressType type){

        if(type == null)
            throw new IllegalArgumentException(
                    "Address type can't be null");

        this.type = type;
    }
}

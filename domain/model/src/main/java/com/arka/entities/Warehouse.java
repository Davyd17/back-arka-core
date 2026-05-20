package com.arka.model;

import com.arka.exceptions.InvalidActivationStateException;
import com.arka.model.information.Address;
import com.arka.util.NullValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class Warehouse {

    private Long id;
    private Boolean active;
    private Address address;

    public static Warehouse create(Address address){

        NullValidator.validate(address, "address");

        return Warehouse.builder()
                .address(address)
                .active(true)
                .build();
    }

    public void activate(){

        if(this.active)
            throw new InvalidActivationStateException(this.getClass(), this.id, this.active);

        this.active = true;
    }

    public void deactivate(){

        if(!this.active)
            throw new InvalidActivationStateException(this.getClass(), this.id, this.active);

        this.active = false;
    }

}

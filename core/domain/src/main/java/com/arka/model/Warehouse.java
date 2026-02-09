package com.arka.model;

import com.arka.model.information.Address;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Warehouse {

    private Long id;
    private Boolean isActive;
    private Address address;
}

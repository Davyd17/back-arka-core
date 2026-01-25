package com.arka.entities.information;

import com.arka.entities.enums.AddressType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private Long id;
    private String country;
    private String city;
    private short zipCode;
    private String address;
    private String notes;
    private AddressType addressType;
    private boolean isActive;
}

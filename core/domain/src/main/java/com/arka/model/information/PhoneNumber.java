package com.arka.model.information;

import lombok.*;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class PhoneNumber {
    private String countryCode;
    private String extension;
    private String phone;
    private boolean active;
    private Instant createdAt;

    public PhoneNumber(){
        this.active = true;
        this.createdAt = Instant.now();
    }
}

package com.arka.model.information;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber {
    private Long id;
    private String countryCode;
    private String extension;
    private String phone;
    private boolean isActive;
    private Instant created_at;
    private Instant updated_at;
}

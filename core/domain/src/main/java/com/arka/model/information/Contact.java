package com.arka.model.information;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    private Long contactId;
    private String name;
    private String lastName;
    private String company;
    private String position;
    private String email;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;
    private Instant created_at;
    private Instant updated_at;
    private boolean isActive;
    private Long userId;
}

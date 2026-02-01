package com.arka.model.information;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Contact {
    private Long contactId;
    private String name;
    private String lastName;
    private String position;
    private String email;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;
    private Instant createdAt;
    private boolean active;
    private Long userId;

    public static Contact create(
            String name,
            String lastName,
            String position,
            String email,
            List<Address> addresses,
            List<PhoneNumber> phoneNumbers,
            Long userId
    ){

        return Contact.builder()
                .name(name)
                .lastName(lastName)
                .position(position)
                .email(email)
                .addresses(List.copyOf(addresses))
                .active(true)
                .createdAt(Instant.now())
                .phoneNumbers(phoneNumbers)
                .userId(userId)
                .build();
    }

}

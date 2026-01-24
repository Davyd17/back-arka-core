package com.arka.entities.party;

import com.arka.entities.information.Contact;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    private Long supplierId;
    private Instant createdAt;
    private Contact contact;
}

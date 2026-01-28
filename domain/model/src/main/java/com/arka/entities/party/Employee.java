package com.arka.entities.party;

import com.arka.entities.information.Contact;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private Long employeeId;
    private int code;
    private Contact contact;

}

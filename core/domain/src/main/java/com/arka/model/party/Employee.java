package com.arka.model.party;

import com.arka.model.information.Contact;
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

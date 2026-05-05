package com.arka.model;

import com.arka.model.information.Contact;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Employee {
    private Long id;
    private int code;
    private Contact contact;

    public static Employee create(int code, Contact contact){
        return Employee.builder()
                .code(code)
                .contact(contact)
                .build();
    }
}

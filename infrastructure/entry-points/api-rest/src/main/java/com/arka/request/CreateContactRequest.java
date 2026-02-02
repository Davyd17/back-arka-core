package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.swing.text.Position;
import java.util.List;

public record CreateContactRequest(

        @Required(field = "Name")
        String name,

        @Required(field = "Last name")
        String lastName,

        @Required(field = "Position")
        String position,


        @Required(field = "Email")
        @Email(message = "Enter a valid email")
        String email,

        @NotEmpty(message =
                "There must be at least one address")
        List<CreateAddressRequest> addresses,

        @NotEmpty(message =
                "There must be at least one phone number")
        List<CreatePhoneNumberRequest> phoneNumbers,

        Long userId
) {
}

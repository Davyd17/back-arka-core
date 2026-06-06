package com.arka.entities.information;

import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.InvalidActivationStateException;
import lombok.*;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class PhoneNumber {
    private Long id;
    private String countryCode;
    private String extension;
    private String phone;
    private boolean active;
    private Instant createdAt;

    public static PhoneNumber create(
            int countryCode,
            String phone){

        return PhoneNumber.builder()
                .countryCode("+" + countryCode)
                .phone(phone)
                .active(true)
                .build();
    }

    public void activate(){

        if(this.active)
            throw new InvalidActivationStateException(
                    PhoneNumber.class, id, this.active);

        this.active = true;
    }

    public void deactivate(){

        if(!this.active)
            throw new InvalidActivationStateException(
                    PhoneNumber.class, id, this.active);

        this.active = false;
    }

    public void addExtension(String ext){

        if(this.extension != null)
            throw new AlreadyExistsException(PhoneNumber.class, id);

        this.extension = ext;
    }
}

package com.arka.model.information;

import com.arka.exceptions.InvalidActivationStateException;
import com.arka.exceptions.NotFoundException;
import com.arka.util.ExistenceValidator;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Contact {
    private Long id;
    @Setter private String name;
    @Setter private String lastName;
    @Setter private String position;
    @Setter private String email;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;
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

        if(addresses == null || addresses.isEmpty())
            throw new IllegalArgumentException(
                    "Contact should have at least one associated address");

        if(phoneNumbers == null || phoneNumbers.isEmpty())
            throw new IllegalArgumentException(
                    "Contact should have at least one associated phone number");

        return Contact.builder()
                .name(name)
                .lastName(lastName)
                .position(position)
                .email(email)
                .addresses(addresses)
                .active(true)
                .phoneNumbers(phoneNumbers)
                .userId(userId)
                .build();
    }

    public void activate(){

        this.validateActivationState(true);
        this.active = true;
    }

    public void deactivate(){

        this.validateActivationState(false);
        this.active = false;
    }

    private void validateActivationState(boolean newState){

        if(this.active == newState)
            throw new InvalidActivationStateException(
                    this.getClass(),
                    this.id,
                    this.active);
    }

    public void addAddress(Address address){

        ExistenceValidator.validateNoDuplicate(
                this.addresses, Address::getId, address.getId(), Address.class);

        this.addresses.add(address);
    }

    public void removeAddress(Long addressId){

        boolean removed = this.addresses.removeIf(a ->
                a.getId().equals(addressId));

        if(!removed)
            throw new NotFoundException(
                    String.format("Address with id [%s] not found", addressId));
    }

    public void addPhoneNumber(PhoneNumber phoneNumber){

        ExistenceValidator.validateNoDuplicate(
                this.phoneNumbers,
                PhoneNumber::getId,
                phoneNumber.getId(),
                PhoneNumber.class);

        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(Long phoneNumberId){

        boolean removed = this.phoneNumbers.removeIf(pn ->
                pn.getId().equals(phoneNumberId));

        if(!removed){
            throw new NotFoundException(
                    String.format("PhoneNumber with id [%s] not found", phoneNumberId));
        }
    }
}

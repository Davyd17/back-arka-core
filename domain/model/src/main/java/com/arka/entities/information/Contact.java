package com.arka.entities.information;

import com.arka.entities.Company;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.InvalidActivationStateException;
import com.arka.exceptions.NotFoundException;
import com.arka.util.ExistenceValidator;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Contact {
    private Long id;
    private String name;
    private String lastName;
    @Nullable private String companyPosition;
    @Nullable private Company company;
    private String email;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;
    private boolean active;

    public static Contact create(
            String name,
            String lastName,
            String email
    ) {

        return Contact.builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .addresses(new ArrayList<>())
                .active(true)
                .phoneNumbers(new ArrayList<>())
                .build();
    }

    public void activate() {

        this.validateActivationState(true);
        this.active = true;
    }

    public void deactivate() {

        this.validateActivationState(false);
        this.active = false;
    }

    private void validateActivationState(boolean newState) {

        if (this.active == newState)
            throw new InvalidActivationStateException(
                    this.getClass(),
                    this.id,
                    this.active);
    }

    public void addAddress(Address address) {

        ExistenceValidator.validateNoDuplicate(
                this.addresses, Address::getId, address.getId(), Address.class);

        this.addresses.add(address);
    }

    public void removeAddress(Long addressId) {

        boolean removed = this.addresses.removeIf(a ->
                a.getId().equals(addressId));

        if (!removed)
            throw new NotFoundException(
                    String.format("Address with id [%s] not found", addressId));
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {

        ExistenceValidator.validateNoDuplicate(
                this.phoneNumbers,
                PhoneNumber::getId,
                phoneNumber.getId(),
                PhoneNumber.class);

        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(Long phoneNumberId) {

        boolean removed = this.phoneNumbers.removeIf(pn ->
                pn.getId().equals(phoneNumberId));

        if (!removed) {
            throw new NotFoundException(
                    String.format("PhoneNumber with id [%s] not found", phoneNumberId));
        }
    }

    public void assignCompany(Company company, String companyPosition){

        if(this.company != null)
            throw new AlreadyExistsException("This contact already has a company assigned");

        this.company = company;
        this.companyPosition = companyPosition;
    }

    public boolean hasCompanyAssigned(){
        return company != null;
    }

}

package com.arka.mappers.response;

import com.arka.model.information.Address;
import com.arka.model.information.Contact;
import com.arka.model.information.PhoneNumber;
import com.arka.response.get.AddressResponse;
import com.arka.response.get.ContactResponse;
import com.arka.response.get.PhoneNumberResponse;
import com.arka.response.save.AddressSaveResponse;
import com.arka.response.save.ContactSaveResponse;
import com.arka.response.save.PhoneNumberSaveResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactResponseMapper {

    ContactResponse toResponse(Contact contact);
    ContactSaveResponse toSaveResponse(Contact domain);

    PhoneNumberResponse phoneToResponse(PhoneNumber domain);
    PhoneNumberSaveResponse phoneNumberToSaveResponse(PhoneNumber domain);

    AddressResponse addressToResponse(Address domain);
    AddressSaveResponse addressToSaveResponse(Address domain);


}

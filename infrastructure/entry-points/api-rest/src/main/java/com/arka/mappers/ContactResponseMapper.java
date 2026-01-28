package com.arka.mappers;

import com.arka.model.information.Contact;
import com.arka.response.ContactResponse;
import com.arka.response.PhoneNumberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {AddressResponseMapper.class, PhoneNumberResponse.class})
public interface ContactResponseMapper {

    @Mapping(target = "isActive", constant = "true")
    ContactResponse toResponse(Contact contact);
}

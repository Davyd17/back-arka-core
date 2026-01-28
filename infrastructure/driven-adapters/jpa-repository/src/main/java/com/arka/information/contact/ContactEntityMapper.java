package com.arka.information.contact;

import com.arka.entities.information.Contact;
import com.arka.information.address.AddressEntityMapper;
import com.arka.information.phonenumber.PhoneNumberEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {AddressEntityMapper.class,
                PhoneNumberEntityMapper.class})

public interface ContactEntityMapper {

    @Mapping(target = "isActive", constant = "true")
    Contact toDomain(ContactEntity contactEntity);
}

package com.arka.mappers.request;

import com.arka.model.information.Address;
import com.arka.model.information.Contact;
import com.arka.model.information.PhoneNumber;
import com.arka.request.CreateAddressRequest;
import com.arka.request.CreateContactRequest;
import com.arka.request.CreatePhoneNumberRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ContactRequestMapper {

    @Mappings({
            @Mapping(target = "active",  ignore = true),
            @Mapping(target = "contactId", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    Contact toDomain(CreateContactRequest request);

    @Mappings({
            @Mapping(target = "active",  ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    PhoneNumber phoneToDomain(CreatePhoneNumberRequest request);

    @Mappings({
            @Mapping(target = "active",  ignore = true),
            @Mapping(target = "type", ignore = true)
    })
    Address addressToDomain(CreateAddressRequest request);
}

package com.arka.mappers;

import com.arka.model.information.PhoneNumber;
import com.arka.response.PhoneNumberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneNumberResponseMapper {

    @Mapping(target = "isActive", constant = "true")
    PhoneNumberResponse toResponse(PhoneNumber phoneNumber);
}

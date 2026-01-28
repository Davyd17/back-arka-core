package com.arka.information.phonenumber;

import com.arka.model.information.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneNumberEntityMapper {

    @Mapping(target = "isActive", constant = "true")
    PhoneNumber toDomain(PhoneNumberEntity entity);
}

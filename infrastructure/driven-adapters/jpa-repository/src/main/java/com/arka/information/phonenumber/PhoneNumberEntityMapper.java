package com.arka.information.phonenumber;

import com.arka.model.information.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PhoneNumberEntityMapper {

    PhoneNumber toDomain(PhoneNumberEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    PhoneNumberEntity phoneNumberToEntity(PhoneNumber domain);
}

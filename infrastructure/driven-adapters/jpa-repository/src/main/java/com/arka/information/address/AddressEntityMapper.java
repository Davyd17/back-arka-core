package com.arka.information.address;

import com.arka.entities.information.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    @Mapping(target = "isActive", constant = "true")
    Address toDomain(AddressesEntity addressesEntity);
}

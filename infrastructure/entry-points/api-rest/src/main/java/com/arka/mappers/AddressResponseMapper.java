package com.arka.mappers;

import com.arka.model.information.Address;
import com.arka.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    @Mapping(target = "isActive", constant = "true")
    AddressResponse toResponse(Address address);
}

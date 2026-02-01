package com.arka.information.address;

import com.arka.model.information.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    Address toDomain(AddressEntity addressEntity);

    @Mapping(target = "id", ignore = true)
    AddressEntity addressToEntity(Address domain);
}

package com.arka.party.supplier;

import com.arka.entities.party.Supplier;
import com.arka.information.contact.ContactEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ContactEntityMapper.class)
public interface SupplierEntityMapper {

    Supplier toDomain(SupplierEntity supplierEntity);
}

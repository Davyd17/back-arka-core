package com.arka.party.supplier;

import com.arka.model.party.Supplier;
import com.arka.information.contact.ContactEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ContactEntityMapper.class)
public interface SupplierEntityMapper {

    Supplier toDomain(SupplierEntity supplierEntity);
}

package com.arka.mappers;

import com.arka.model.party.Supplier;
import com.arka.response.SupplierResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {ProductCategoryResponseMapper.class, ContactResponseMapper.class})
public interface SupplierResponseMapper {

    SupplierResponse toResponse(Supplier domain);
}

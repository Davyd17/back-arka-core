package com.arka.mappers;

import com.arka.model.Company;
import com.arka.response.CompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {ProductCategoryResponseMapper.class, ContactResponseMapper.class})
public interface SupplierResponseMapper {

    CompanyResponse toResponse(Company domain);
}

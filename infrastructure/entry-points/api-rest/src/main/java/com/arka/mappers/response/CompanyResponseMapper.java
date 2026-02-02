package com.arka.mappers.response;

import com.arka.model.Company;
import com.arka.response.get.CompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {ProductCategoryResponseMapper.class,
                ContactResponseMapper.class})

public interface CompanyResponseMapper {

    CompanyResponse toResponse(Company domain);
}

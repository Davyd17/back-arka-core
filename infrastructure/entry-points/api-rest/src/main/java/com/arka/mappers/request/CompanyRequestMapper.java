package com.arka.mappers.request;

import com.arka.dto.request.CreateCompany;
import com.arka.request.CreateCompanyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
        ContactRequestMapper.class,
        SlugProductCategoryRequestMapper.class
})
public interface CompanyRequestMapper {

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "relation", ignore = true)
    })
    CreateCompany toDomain(CreateCompanyRequest request);
}

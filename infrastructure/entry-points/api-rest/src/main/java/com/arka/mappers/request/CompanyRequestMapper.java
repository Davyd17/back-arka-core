package com.arka.mappers.request;

import com.arka.dto.in.CreateCompanyIn;
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
    CreateCompanyIn toDomain(CreateCompanyRequest request);
}

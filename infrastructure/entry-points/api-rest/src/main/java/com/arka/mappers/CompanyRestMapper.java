package com.arka.mappers;

import com.arka.party.dto.CompanyOut;
import com.arka.party.dto.CreateSupplierIn;
import com.arka.request.CreateCompanyRequest;
import com.arka.response.get.CompanyResponse;
import com.arka.response.save.CreateCompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        ProductRestMapper.class,
        ContactRestMapper.class
})
public interface CompanyRestMapper {

    @Mapping(target = "createdAt", ignore = true)
    CompanyResponse toResponse(CompanyOut output);

    CreateSupplierIn toInput(CreateCompanyRequest request);

    CreateCompanyResponse toCreateResponse(CompanyOut output);
}

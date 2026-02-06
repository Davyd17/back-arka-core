package com.arka.mapper;

import com.arka.dto.out.CompanyCreateOrderOut;
import com.arka.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CreateCompanyMapper {

    CompanyCreateOrderOut toDTO(Company domain);
}

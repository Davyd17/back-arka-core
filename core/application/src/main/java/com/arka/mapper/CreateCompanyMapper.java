package com.arka.mapper;

import com.arka.dto.out.CompanySummaryOut;
import com.arka.model.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCompanyMapper {

    CompanySummaryOut toDTO(Company domain);
}

package com.arka.mapper;

import com.arka.dto.out.CompanyOut;
import com.arka.model.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {

    CompanyOut toOut(Company domain);
}

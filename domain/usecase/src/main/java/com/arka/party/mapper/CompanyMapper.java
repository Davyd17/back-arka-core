package com.arka.party.mapper;

import com.arka.party.dto.CompanyOut;
import com.arka.model.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {

    CompanyOut toOut(Company domain);
}

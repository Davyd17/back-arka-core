package com.arka.party.mapper;

import com.arka.entities.Company;
import com.arka.party.dto.CompanyOut;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {

    CompanyOut toOut(Company domain);
}

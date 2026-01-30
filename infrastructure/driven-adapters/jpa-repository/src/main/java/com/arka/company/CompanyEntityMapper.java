package com.arka.company;

import com.arka.model.Company;
import com.arka.information.contact.ContactEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ContactEntityMapper.class)
public interface CompanyEntityMapper {

    Company toDomain(CompanyEntity companyEntity);
}

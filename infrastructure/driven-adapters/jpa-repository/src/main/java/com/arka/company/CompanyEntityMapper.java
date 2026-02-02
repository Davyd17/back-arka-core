package com.arka.company;

import com.arka.model.Company;
import com.arka.information.contact.ContactEntityMapper;
import com.arka.product.category.ProductCategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {ContactEntityMapper.class,
                ProductCategoryMapper.class})
public interface CompanyEntityMapper {

    Company toDomain(CompanyEntity companyEntity);

    @Mapping(target = "updatedAt", ignore = true)
    CompanyEntity toEntity(Company company);

}

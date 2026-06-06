package com.arka.company;

import com.arka.entities.Company;
import com.arka.information.contact.ContactEntityMapper;
import com.arka.product.category.ProductCategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        uses = {ContactEntityMapper.class,
                ProductCategoryMapper.class})
public interface CompanyEntityMapper {

    Company toDomain(CompanyEntity companyEntity);

    @Mappings({
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    CompanyEntity toEntity(Company company);

}

package com.arka.company;

import com.arka.information.address.AddressEntity;
import com.arka.information.contact.ContactEntity;
import com.arka.information.phonenumber.PhoneNumberEntity;
import com.arka.model.Company;
import com.arka.information.contact.ContactEntityMapper;
import com.arka.model.information.Address;
import com.arka.model.information.Contact;
import com.arka.model.information.PhoneNumber;
import com.arka.model.product.ProductCategory;
import com.arka.product.ProductCategoryEntity;
import com.arka.product.ProductCategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        uses = {ContactEntityMapper.class,
                ProductCategoryMapper.class})
public interface CompanyEntityMapper {

    Company toDomain(CompanyEntity companyEntity);

    @Mapping(target = "updatedAt", ignore = true)
    CompanyEntity toEntity(Company company);

}

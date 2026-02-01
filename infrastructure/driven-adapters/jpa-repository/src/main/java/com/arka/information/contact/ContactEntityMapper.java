package com.arka.information.contact;

import com.arka.model.information.Contact;
import com.arka.information.address.AddressEntityMapper;
import com.arka.information.phonenumber.PhoneNumberEntityMapper;
import com.arka.product.ProductCategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        uses = {AddressEntityMapper.class,
                PhoneNumberEntityMapper.class})

public interface ContactEntityMapper {

    Contact toDomain(ContactEntity contactEntity);

    @Mappings({
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "company", ignore = true)
    })
    ContactEntity contactToEntity(Contact domain);

}

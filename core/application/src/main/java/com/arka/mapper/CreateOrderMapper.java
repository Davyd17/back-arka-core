package com.arka.mapper;

import com.arka.dto.in.CompanyNameIn;
import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.out.CompanyCreateOrderOut;
import com.arka.dto.out.CreateOrderOut;
import com.arka.model.Company;
import com.arka.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CreateOrderMapper {

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Order toDomain(CreateOrderIn in);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "contacts", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productCategories", ignore = true),
            @Mapping(target = "relation", ignore = true),
    })
    Company toCompanyDomain(CompanyNameIn in);

    CreateOrderOut toDTO(Order domain);

    CompanyCreateOrderOut toCompanyDTO(Company domain);

}

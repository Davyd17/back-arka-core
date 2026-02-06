package com.arka.mapper;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses =
        {CreateOrderItemMapper.class,
        CreateCompanyMapper.class})
public interface CreateOrderMapper {

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "company.id", source = "companyId")
    })
    Order toDomain(CreateOrderIn in);

    CreateOrderOut toDTO(Order domain);

}

package com.arka.mappers.response;

import com.arka.model.Company;
import com.arka.response.save.CompanySaveResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {ProductCategoryResponseMapper.class,
                ContactResponseMapper.class})

public interface CompanySaveResponseMapper {

    CompanySaveResponse toSaveResponse(Company domain);
}

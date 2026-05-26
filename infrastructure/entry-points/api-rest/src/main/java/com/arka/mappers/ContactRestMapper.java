package com.arka.mappers;

import com.arka.party.dto.ContactOut;
import com.arka.response.get.ContactResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactRestMapper {


    ContactResponse toResponse(ContactOut output);
}

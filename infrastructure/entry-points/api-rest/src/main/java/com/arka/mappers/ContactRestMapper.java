package com.arka.mappers;

import com.arka.party.dto.ContactOut;
import com.arka.party.dto.ContactOutput;
import com.arka.party.dto.CreateContactInput;
import com.arka.request.CreateContactRequest;
import com.arka.response.get.ContactResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactRestMapper {

    ContactResponse toResponse(ContactOutput output);

    CreateContactInput toCreateInput(CreateContactRequest request);
}

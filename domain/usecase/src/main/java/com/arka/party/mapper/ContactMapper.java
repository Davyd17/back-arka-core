package com.arka.party.mapper;

import com.arka.entities.information.Contact;
import com.arka.party.dto.ContactOutput;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {
    ContactOutput toOutput(Contact contact);
}

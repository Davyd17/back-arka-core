package com.arka.party;

import com.arka.entities.information.Contact;
import com.arka.party.dto.ContactOutput;
import com.arka.party.dto.CreateContactInput;
import com.arka.party.gateway.ContactGateway;
import com.arka.party.mapper.ContactMapper;
import com.arka.party.service.ContactService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class CreateContactUseCase {

    private final ContactService contactService;

    private final ContactMapper contactMapper =
            Mappers.getMapper(ContactMapper.class);

    public ContactOutput execute(CreateContactInput input){

        NullValidator.validate(input, "input");

        Contact newContact = Contact.create(
                input.name(), input.lastName(), input.email());

        return contactMapper.toOutput(contactService.save(newContact));
    }
}

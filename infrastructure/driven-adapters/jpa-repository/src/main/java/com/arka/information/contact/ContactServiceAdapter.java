package com.arka.information.contact;

import com.arka.entities.information.Contact;
import com.arka.party.gateway.ContactGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceAdapter implements ContactGateway {

    private final ContactRepository repository;
    private final ContactEntityMapper mapper;

    @Override
    public List<Contact> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Contact save(Contact contact) {
        ContactEntity contactEntity = mapper.toEntity(contact);
        return mapper.toDomain(repository.save(contactEntity));
    }
}

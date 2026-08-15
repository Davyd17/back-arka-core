package com.arka.party.gateway;

import com.arka.entities.information.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactGateway {

    List<Contact> findAllByIds(List<Long> ids);

    boolean existsByEmail(String email);

    Contact save(Contact contact);

    Optional<Contact> findByEmail(String email);
}

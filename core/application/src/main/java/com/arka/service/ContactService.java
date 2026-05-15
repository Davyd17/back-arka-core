package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.ContactGateway;
import com.arka.model.information.Contact;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ContactService {

    private final ContactGateway contactGateway;

    public List<Contact> findAllByIds(Set<Long> ids){

        List<Contact> foundContacts =
                contactGateway.findAllByIds(new ArrayList<>(ids));

        if(foundContacts.size() != ids.size())
            throw new NotFoundException("one or more contacts not found");

        return foundContacts;
    }
}

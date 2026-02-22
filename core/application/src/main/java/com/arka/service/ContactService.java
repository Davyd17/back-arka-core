package com.arka.service;

import com.arka.gateway.repository.ContactGateway;
import com.arka.model.information.Contact;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactService {

    private final ContactGateway gateway;

    public Contact getById(Long id){

        if (id == null)
            throw new IllegalArgumentException
                    ("Contact id can't be null");

        return gateway.getById(id);
    }
}

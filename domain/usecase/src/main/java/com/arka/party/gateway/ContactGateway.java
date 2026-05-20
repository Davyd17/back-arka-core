package com.arka.party.gateway;

import com.arka.model.information.Contact;

import java.util.List;

public interface ContactGateway {

    List<Contact> findAllByIds(List<Long> ids);
}

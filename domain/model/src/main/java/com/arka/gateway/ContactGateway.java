package com.arka.gateway;

import com.arka.model.information.Contact;

import java.util.List;
import java.util.Set;

public interface ContactGateway {

    List<Contact> findAllByIds(List<Long> ids);
}

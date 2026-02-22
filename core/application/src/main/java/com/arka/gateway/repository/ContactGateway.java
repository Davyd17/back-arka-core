package com.arka.gateway.repository;

import com.arka.model.information.Contact;

public interface ContactGateway {

    Contact getById(Long id);
}

package com.arka.gateway.repository;

import com.arka.model.information.Address;

import java.util.Optional;

public interface AddressGateway {

    Optional<Address> findById(Long id);
}

package com.arka.party.gateway;

import com.arka.entities.information.Address;

import java.util.Optional;

public interface AddressGateway {

    Optional<Address> findById(Long id);
}

package com.arka.party.service;

import com.arka.entities.information.Address;
import com.arka.exceptions.NotFoundException;
import com.arka.party.gateway.AddressGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressService {

    private final AddressGateway gateway;

    public Address findById(Long id) {
            return gateway.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Address with id %s not found", id)
                    ));
    }
}

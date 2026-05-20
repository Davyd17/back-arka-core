package com.arka.party.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.AddressGateway;
import com.arka.model.information.Address;
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

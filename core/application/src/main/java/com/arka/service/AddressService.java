package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.AddressGateway;
import com.arka.model.information.Address;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AddressService {

    private final AddressGateway gateway;

    public Address findById(Long id) {

        if (Objects.nonNull(id))
            return gateway.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Address with id %s not found", id)
                    ));

        else throw new IllegalArgumentException("Address id cannot be null");
    }
}

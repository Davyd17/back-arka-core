package com.arka.information.address;

import com.arka.entities.information.Address;
import com.arka.party.gateway.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressEntityServiceAdapter implements AddressGateway {

    private final AddressEntityRepository repository;

    private final AddressEntityMapper mapper;

    @Override
    public Optional<Address> findById(Long id) {

        if(id == null)
            throw new IllegalArgumentException(
                    "Address id cannot be null");

        return repository.findById(id).
                map(mapper::toDomain);
    }
}

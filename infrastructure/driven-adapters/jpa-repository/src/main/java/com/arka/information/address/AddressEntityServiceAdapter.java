package com.arka.information.address;

import com.arka.gateway.repository.AddressGateway;
import com.arka.model.information.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressEntityServiceAdapter implements AddressGateway {

    private final AddressEntityRepository repository;

    private final AddressEntityMapper mapper;

    @Override
    public Optional<Address> findById(Long id) {

        if(Objects.nonNull(id))
            return repository.findById(id).
                    map(mapper::toDomain);

        else throw new IllegalArgumentException(
                "Address id cannot be null"
        );
    }
}

package com.arka.warehouse;

import com.arka.gateway.repository.WarehouseGateway;
import com.arka.model.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceAdapter implements WarehouseGateway {

    private final WarehouseRepository repository;

    private final WarehouseEntityMapper mapper;

    @Override
    public Optional<Warehouse> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}

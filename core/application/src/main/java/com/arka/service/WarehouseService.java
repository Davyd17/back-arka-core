package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.WarehouseGateway;
import com.arka.model.Warehouse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseGateway gateway;

    public Warehouse findById(Long id){
        return gateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Warehouse with id %d not found", id)));
    }
}

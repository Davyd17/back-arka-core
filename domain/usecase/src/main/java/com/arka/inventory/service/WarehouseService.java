package com.arka.inventory.service;

import com.arka.entities.Warehouse;
import com.arka.exceptions.NotFoundException;
import com.arka.inventory.gateway.WarehouseGateway;
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

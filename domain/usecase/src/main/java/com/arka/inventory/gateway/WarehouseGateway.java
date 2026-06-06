package com.arka.inventory.gateway;

import com.arka.entities.Warehouse;

import java.util.Optional;

public interface WarehouseGateway {

    Optional<Warehouse> findById(Long id);
}

package com.arka.gateway.repository;

import com.arka.model.Warehouse;

import java.util.Optional;

public interface WarehouseGateway {

    Optional<Warehouse> findById(Long id);
}

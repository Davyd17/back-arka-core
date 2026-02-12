package com.arka.inventory.warehouse;

import com.arka.gateway.inventory.WarehouseInventoryGateway;
import com.arka.model.inventory.WarehouseInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseInventoryServiceAdapter implements WarehouseInventoryGateway {

    private final WarehouseInventoryRepository repository;

    private final WarehouseInventoryEntityMapper mapper;

    @Override
    public Optional<WarehouseInventory> findInventoryByWarehouseAndProduct(Long locationId, Long productId) {
        return repository.findByProductIdAndWarehouseId(locationId, productId)
                .map(mapper::toDomain);
    }

    @Override
    public WarehouseInventory save(WarehouseInventory inventory) {

        WarehouseInventoryEntity inventoryEntity =
                 mapper.toEntity(inventory) ;

        return mapper.toDomain(repository.save(inventoryEntity));
    }

    @Override
    public List<WarehouseInventory> listLowStockInventoryByWarehouseId(
            Long warehouseInventoryId, int threshold) {

        return repository.findLowStockInventoryByWarehouseId(warehouseInventoryId, threshold)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}

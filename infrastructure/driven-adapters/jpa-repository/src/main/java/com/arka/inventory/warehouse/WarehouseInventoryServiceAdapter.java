package com.arka.inventory.warehouse;

import com.arka.entities.inventory.WarehouseInventory;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.inventory.movements.InventoryMovementEntity;
import com.arka.report.dto.LowStockReportData;
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
                mapper.toEntity(inventory);

        for(InventoryMovementEntity movement : inventoryEntity.getInventoryMovements()){
                movement.setWarehouseInventory(inventoryEntity);
        }

        return mapper.toDomain(repository.save(inventoryEntity));
    }

    @Override
    public List<LowStockReportData.Item> listLowStockInventoryByWarehouseId(
            Long warehouseId, int threshold) {

        return repository.findLowStockInventoryByWarehouseId(warehouseId, threshold)
                .stream()
                .map(view -> new LowStockReportData.Item(
                        view.getProductId(),
                        view.getStock(),
                        view.getSku(),
                        view.getProductName(),
                        view.getProductCategory()
                ))
                .toList();
    }

    @Override
    public int getTotalStockByProductId(Long productId) {
        return repository.getTotalStockByProductId(productId);
    }
}

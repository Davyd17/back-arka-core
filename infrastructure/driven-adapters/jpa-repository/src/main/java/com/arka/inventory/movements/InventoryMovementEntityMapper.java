package com.arka.inventory.movements;

import com.arka.entities.inventory.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMovementEntityMapper {

    @Mapping(target = "warehouseInventory", ignore = true)
    InventoryMovementEntity toEntity(InventoryMovement inventoryMovement);

    InventoryMovement toDomain(InventoryMovementEntity inventoryMovementEntity);
}

package com.arka.inventory.movements;

import com.arka.model.inventory.InventoryMovement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementEntityMapper {

    InventoryMovementEntity toEntity(InventoryMovement inventoryMovement);

    InventoryMovement toDomain(InventoryMovementEntity inventoryMovementEntity);
}

package com.arka.gateway.repository.inventory;

import com.arka.model.inventory.InventoryMovement;

public interface InventoryMovementGateway {

    InventoryMovement save(InventoryMovement newMovement);
}

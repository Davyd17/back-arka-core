package com.arka.gateway.inventory;

import com.arka.model.inventory.InventoryMovement;

public interface InventoryMovementGateway {

    InventoryMovement save(InventoryMovement newMovement);
}

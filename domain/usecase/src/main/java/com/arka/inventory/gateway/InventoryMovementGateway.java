package com.arka.inventory.gateway;

import com.arka.model.inventory.InventoryMovement;

public interface InventoryMovementGateway {

    InventoryMovement save(InventoryMovement newMovement);
}

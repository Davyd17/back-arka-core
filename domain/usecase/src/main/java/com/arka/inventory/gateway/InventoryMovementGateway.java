package com.arka.inventory.gateway;

import com.arka.entities.inventory.InventoryMovement;

public interface InventoryMovementGateway {

    InventoryMovement save(InventoryMovement newMovement);
}

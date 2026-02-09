package com.arka.inventory.movements;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository
        extends JpaRepository<InventoryMovementEntity, Long> {


}

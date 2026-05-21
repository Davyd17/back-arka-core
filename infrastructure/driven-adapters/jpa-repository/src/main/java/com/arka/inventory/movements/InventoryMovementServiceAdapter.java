package com.arka.inventory.movements;

import com.arka.entities.inventory.InventoryMovement;
import com.arka.inventory.gateway.InventoryMovementGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceAdapter implements InventoryMovementGateway {

    private final InventoryMovementRepository repository;

    private final InventoryMovementEntityMapper mapper;

    @Override
    public InventoryMovement save(InventoryMovement newMovement) {

        InventoryMovementEntity inventoryEntity =
                mapper.toEntity(newMovement) ;

        return mapper.toDomain(repository.save(inventoryEntity));
    }
}

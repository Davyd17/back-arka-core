package com.arka.inventory.movements;

import com.arka.gateway.repository.inventory.InventoryMovementGateway;
import com.arka.model.inventory.InventoryMovement;
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

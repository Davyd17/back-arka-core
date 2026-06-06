package com.arka.inventory.warehouse;

import com.arka.entities.inventory.InventoryMovement;
import com.arka.entities.inventory.WarehouseInventory;
import com.arka.inventory.movements.InventoryMovementEntity;
import com.arka.inventory.movements.InventoryMovementEntityMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class WarehouseInventoryEntityMapper {

    @Autowired
    private InventoryMovementEntityMapper movementMapper;

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    public abstract WarehouseInventoryEntity toEntity(WarehouseInventory entity);

    public abstract WarehouseInventory toDomain(WarehouseInventoryEntity entity);

    public Deque<InventoryMovement> mapListToDeque(List<InventoryMovementEntity> list) {
        if (list == null) {
            return null;
        }

        Deque<InventoryMovement> deque = new ArrayDeque<>();
        list.forEach(movement ->
                deque.add(movementMapper.toDomain(movement)));

        return deque;
    }

    public List<InventoryMovementEntity> mapDequeToList(Deque<InventoryMovement> deque) {
        if (deque == null) {
            return null;
        }

        return deque.stream()
                .map(movementMapper::toEntity)
                .toList();
    }
}

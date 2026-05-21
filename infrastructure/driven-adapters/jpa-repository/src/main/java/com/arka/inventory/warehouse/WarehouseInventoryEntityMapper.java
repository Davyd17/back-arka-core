package com.arka.inventory.warehouse;

import com.arka.entities.inventory.WarehouseInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WarehouseInventoryEntityMapper {

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    WarehouseInventoryEntity toEntity(WarehouseInventory entity);

    @Mapping(target = "inventoryMovements", ignore = true)
    WarehouseInventory toDomain(WarehouseInventoryEntity entity);
}

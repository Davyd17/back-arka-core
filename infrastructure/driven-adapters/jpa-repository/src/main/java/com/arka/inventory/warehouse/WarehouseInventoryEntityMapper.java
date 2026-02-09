package com.arka.inventory.warehouse;

import com.arka.model.inventory.WarehouseInventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseInventoryEntityMapper {

        WarehouseInventoryEntity toEntity(WarehouseInventory entity);

        WarehouseInventory toDomain(WarehouseInventoryEntity entity);
}

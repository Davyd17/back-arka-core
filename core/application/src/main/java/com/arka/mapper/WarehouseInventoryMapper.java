package com.arka.mapper;

import com.arka.dto.out.WarehouseInventoryOut;
import com.arka.model.inventory.WarehouseInventory;
import org.mapstruct.Mapper;

@Mapper
public interface WarehouseInventoryMapper {

    WarehouseInventoryOut toOutDTO(WarehouseInventory warehouseInventory);
}

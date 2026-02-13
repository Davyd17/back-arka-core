package com.arka.mapper;

import com.arka.dto.out.LowStockItemOut;
import com.arka.model.inventory.WarehouseInventory;
import org.mapstruct.Mapper;

@Mapper(uses = ProductSummaryOutMapper.class)
public interface WarehouseInventoryMapper {

    LowStockItemOut toOutDTO(WarehouseInventory warehouseInventory);
}

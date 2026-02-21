package com.arka.mapper;

import com.arka.dto.value.LowStockItem;
import com.arka.model.inventory.WarehouseInventory;
import org.mapstruct.Mapper;

@Mapper(uses = ProductSummaryOutMapper.class)
public interface WarehouseInventoryMapper {

    LowStockItem toOutDTO(WarehouseInventory warehouseInventory);
}

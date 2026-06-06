package com.arka.inventory.mapper;

import com.arka.entities.inventory.WarehouseInventory;
import com.arka.report.dto.LowStockItem;
import com.arka.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = ProductMapper.class)
public interface WarehouseInventoryMapper {

    LowStockItem toOutDTO(WarehouseInventory warehouseInventory);
}

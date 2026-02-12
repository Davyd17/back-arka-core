package com.arka.mappers;

import com.arka.dto.out.WarehouseInventoryOut;
import com.arka.response.get.WarehouseInventoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseInventoryRestMapper {

    WarehouseInventoryResponse toResponse
            (WarehouseInventoryOut warehouseInventoryOut);
}

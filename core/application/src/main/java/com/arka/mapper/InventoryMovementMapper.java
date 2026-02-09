package com.arka.mapper;

import com.arka.dto.in.CreateInventoryMovementIn;
import com.arka.model.inventory.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface InventoryMovementMapper {

    @Mappings({

            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "employee.employeeId", source = "employeeId"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "warehouse.id", source = "warehouseId"),
            @Mapping(target = "newStock", ignore = true),
            @Mapping(target = "previousStock", ignore = true),
            @Mapping(target = "registeredAt", ignore = true)
    })
    InventoryMovement toDomain(CreateInventoryMovementIn inDTO);
}


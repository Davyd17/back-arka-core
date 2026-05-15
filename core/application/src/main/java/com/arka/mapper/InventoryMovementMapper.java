package com.arka.mapper;

import com.arka.dto.in.CreateInventoryMovementIn;
import com.arka.dto.out.CreateInventoryMovementOut;
import com.arka.model.inventory.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {
        EmployeeMapper.class,
        ProductMapper.class
})
public interface InventoryMovementMapper {

    CreateInventoryMovementOut toOut(InventoryMovement domain);
}


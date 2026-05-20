package com.arka.inventory.mapper;

import com.arka.entities.inventory.InventoryMovement;
import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.party.mapper.EmployeeMapper;
import com.arka.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {
        EmployeeMapper.class,
        ProductMapper.class
})
public interface InventoryMovementMapper {

    CreateInventoryMovementOut toOut(InventoryMovement domain);
}


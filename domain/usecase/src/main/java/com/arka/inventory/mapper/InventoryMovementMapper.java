package com.arka.inventory.mapper;

import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.party.mapper.EmployeeMapper;
import com.arka.product.mapper.ProductMapper;
import com.arka.model.inventory.InventoryMovement;
import org.mapstruct.Mapper;

@Mapper(uses = {
        EmployeeMapper.class,
        ProductMapper.class
})
public interface InventoryMovementMapper {

    CreateInventoryMovementOut toOut(InventoryMovement domain);
}


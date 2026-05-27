package com.arka.mappers;

import com.arka.inventory.dto.CreateInventoryMovementIn;
import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.product.mapper.ProductMapper;
import com.arka.request.CreateInventoryMovementRequest;
import com.arka.response.save.CreateInventoryMovementResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface InventoryMovementRestMapper {

        CreateInventoryMovementIn toInput(CreateInventoryMovementRequest request);

        CreateInventoryMovementResponse toCreateResponse(CreateInventoryMovementOut output);
}

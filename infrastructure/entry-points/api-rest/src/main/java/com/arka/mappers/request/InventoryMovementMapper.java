package com.arka.mappers.request;

import com.arka.inventory.dto.CreateInventoryMovementIn;
import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.product.mapper.ProductMapper;
import com.arka.request.CreateInventoryMovementRequest;
import com.arka.response.save.CreateInventoryMovementResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface InventoryMovementMapper {

        CreateInventoryMovementIn toInput(CreateInventoryMovementRequest request);

        CreateInventoryMovementResponse toCreateResponse(CreateInventoryMovementOut output);
}

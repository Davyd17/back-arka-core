package com.arka.mappers;

import com.arka.dto.in.CreateInventoryMovementIn;
import com.arka.request.CreateInventoryMovementRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementRequestMapper {

        CreateInventoryMovementRequest toRequest(CreateInventoryMovementIn inDTO);

        CreateInventoryMovementIn toInDTO(CreateInventoryMovementRequest domain);
}

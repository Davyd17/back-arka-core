package com.arka.warehouse;

import com.arka.model.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseEntityMapper {

    WarehouseEntity toEntity(Warehouse warehouse);

    Warehouse toDomain(WarehouseEntity entity);
}

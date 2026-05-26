package com.arka.warehouse;

import com.arka.entities.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseEntityMapper {

    @Mapping(target = "createdAt", ignore = true)
    WarehouseEntity toEntity(Warehouse warehouse);

    Warehouse toDomain(WarehouseEntity entity);
}

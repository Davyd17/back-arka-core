package com.arka.employee;

import com.arka.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    @Mapping(target = "createdAt", ignore = true)
    EmployeeEntity toEntity(Employee employee);

    Employee toDomain(EmployeeEntity entity);
}

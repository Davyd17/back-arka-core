package com.arka.employee;

import com.arka.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    EmployeeEntity toEntity(Employee employee);

    Employee toDomain(EmployeeEntity entity);
}

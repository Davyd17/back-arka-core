package com.arka.gateway;

import com.arka.model.Employee;

import java.util.Optional;

public interface EmployeeGateway {

    Optional<Employee> findById(Long id);
}

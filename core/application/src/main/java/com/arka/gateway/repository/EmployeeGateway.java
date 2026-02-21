package com.arka.gateway.repository;

import com.arka.model.Employee;

import java.util.Optional;

public interface EmployeeGateway {

    Optional<Employee> findById(Long id);
}

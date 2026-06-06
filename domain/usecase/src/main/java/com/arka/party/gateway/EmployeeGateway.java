package com.arka.party.gateway;

import com.arka.entities.Employee;

import java.util.Optional;

public interface EmployeeGateway {

    Optional<Employee> findById(Long id);
}

package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.EmployeeGateway;
import com.arka.model.Employee;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeGateway gateway;

    public Employee findById(Long id){

        return gateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Employee with id %d not found", id)));
    }
}

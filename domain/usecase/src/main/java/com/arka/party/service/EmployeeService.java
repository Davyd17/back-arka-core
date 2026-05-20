package com.arka.party.service;

import com.arka.entities.Employee;
import com.arka.exceptions.NotFoundException;
import com.arka.party.gateway.EmployeeGateway;
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

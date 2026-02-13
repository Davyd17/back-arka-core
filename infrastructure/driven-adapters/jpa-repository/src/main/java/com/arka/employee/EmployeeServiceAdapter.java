package com.arka.employee;

import com.arka.repository.EmployeeGateway;
import com.arka.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceAdapter implements EmployeeGateway {

    private final EmployeeRepository repository;

    private final EmployeeEntityMapper mapper;

    @Override
    public Optional<Employee> findById(Long id) {

        return repository.findById(id)
                .map(mapper::toDomain);
    }
}

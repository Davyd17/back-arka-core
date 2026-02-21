package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.party.CompanyGateway;
import com.arka.model.Company;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CompanyService {

    private final CompanyGateway companyGateway;

    public Company getCompanyById(Long id) {

        if(Objects.nonNull(id)) {

            return Optional.of(companyGateway.getCompanyById(id))
                    .orElseThrow(() -> new NotFoundException(
                            "Company not found, please enter a valid id"
                    ));

        } else throw new IllegalArgumentException("Name can't be null");
    }
}

package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.party.CompanyGateway;
import com.arka.model.Company;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CompanyService {

    private final CompanyGateway companyGateway;

    public Company getCompanyByName(String name) {

        if(!name.isBlank()) {

            return Optional.of(companyGateway.getCompanyByName(name))
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Company %s not found", name)
                    ));

        } else throw new IllegalArgumentException("Name can't be null");
    }
}

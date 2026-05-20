package com.arka.party.service;

import com.arka.exceptions.NotFoundException;
import com.arka.party.gateway.CompanyGateway;
import com.arka.model.Company;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CompanyService {

    private final CompanyGateway companyGateway;

    public Company findById(Long id) {

        if(Objects.nonNull(id)) {

            return Optional.of(companyGateway.getCompanyById(id))
                    .orElseThrow(() -> new NotFoundException(
                            "Company not found, please enter a valid id"
                    ));

        } else throw new IllegalArgumentException("Name can't be null");
    }
}

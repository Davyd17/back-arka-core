package com.arka.party.gateway;

import com.arka.entities.Company;

public interface CompanyGateway {

    Company getCompanyById(Long id);

    Company createCompany(Company company);
}

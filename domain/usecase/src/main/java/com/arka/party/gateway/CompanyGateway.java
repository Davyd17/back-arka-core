package com.arka.party.gateway;

import com.arka.model.Company;

public interface CompanyGateway {

    Company getCompanyById(Long id);

    Company createCompany(Company company);
}

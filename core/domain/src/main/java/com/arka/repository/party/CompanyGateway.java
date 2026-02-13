package com.arka.repository.party;

import com.arka.model.Company;

public interface CompanyGateway {

    Company getCompanyById(Long id);

    Company createCompany(Company company);
}

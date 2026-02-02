package com.arka.gateway.party;

import com.arka.model.Company;

public interface CompanyGateway {

    Company getCompanyByName(String name);

    Company createCompany(Company company);
}

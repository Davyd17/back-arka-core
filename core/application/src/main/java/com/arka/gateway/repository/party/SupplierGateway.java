package com.arka.gateway.repository.party;

import com.arka.model.Company;

import java.util.List;

public interface SupplierGateway extends CompanyGateway{

    List<Company> getSuppliersByProductCategoryId(Long categoryId);

}

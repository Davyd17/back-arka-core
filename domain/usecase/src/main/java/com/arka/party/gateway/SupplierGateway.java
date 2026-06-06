package com.arka.party.gateway;

import com.arka.entities.Company;

import java.util.List;

public interface SupplierGateway extends CompanyGateway{

    List<Company> getSuppliersByProductCategoryId(Long categoryId);

}

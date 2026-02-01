package com.arka.gateway;

import com.arka.model.Company;

import java.util.List;

public interface SupplierGateway {

    List<Company> getSuppliersByProductCategoryId(Long categoryId);

    Company createSupplier(Company supplier);
}

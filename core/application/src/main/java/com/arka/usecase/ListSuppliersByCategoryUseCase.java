package com.arka.usecase;

import com.arka.model.Company;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.ProductCategoryGateway;
import com.arka.gateway.SupplierGateway;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ListSuppliersByCategoryUseCase {

    private final ProductCategoryGateway productCategoryGateway;
    private final SupplierGateway supplierGateway;

    public List<Company> execute(String categorySlug){

        List<Company> foundCompanies = new ArrayList<>();

        productCategoryGateway
               .findProductCategoryBySlug(categorySlug)
               .ifPresentOrElse(productCategory -> {

                   foundCompanies.addAll(supplierGateway
                           .getSuppliersByProductCategoryId(productCategory.id()));

               }, () -> { throw new NotFoundException("Category not found");});

        return foundCompanies;

    }
}

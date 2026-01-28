package com.arka.usecase;

import com.arka.model.party.Supplier;
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

    public List<Supplier> execute(String categorySlug){

        List<Supplier> foundSuppliers = new ArrayList<>();

        productCategoryGateway
               .findProductCategoryBySlug(categorySlug)
               .ifPresentOrElse(productCategory -> {

                   foundSuppliers.addAll(supplierGateway
                           .getSuppliersByProductCategoryId(productCategory.getId()));

               }, () -> { throw new NotFoundException("Category not found");});

        return foundSuppliers;

    }
}

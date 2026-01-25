package com.arka;

import com.arka.entities.party.Supplier;
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

    public List<Supplier> execute(String categoryName){

        List<Supplier> foundSuppliers = new ArrayList<>();

        productCategoryGateway
               .findProductCategoryByName(categoryName)
               .ifPresentOrElse(productCategory -> {

                   foundSuppliers.addAll(supplierGateway
                           .GetSuppliersByProductCategoryId(productCategory.getId()));

               }, () -> { throw new NotFoundException("Category not found");});

        return foundSuppliers;

    }
}

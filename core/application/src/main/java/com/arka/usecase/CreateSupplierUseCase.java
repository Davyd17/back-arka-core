package com.arka.usecase;

import com.arka.dto.in.CreateCompanyIn;

import com.arka.gateway.SupplierGateway;
import com.arka.model.Company;
import com.arka.model.enums.CompanyRelationType;
import com.arka.model.factory.CompanyFactory;
import com.arka.model.product.ProductCategory;
import com.arka.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateSupplierUseCase {

    private final ProductCategoryService productCategoryService;
    private final SupplierGateway supplierGateway;
    private final CompanyFactory companyFactory = new CompanyFactory();



    public Company execute(CreateCompanyIn request) {

        return Optional.ofNullable(request)
                .map(r -> {

                    Set<ProductCategory> categories = request
                            .getSlugProductCategories()
                            .stream()
                            .map(category ->
                                    productCategoryService.getBySlug(category.slug()))
                            .collect(Collectors.toSet());

                    return supplierGateway
                            .createSupplier(companyFactory
                                    .createCompanyWithFullInfo(
                                            request.getName(),
                                            CompanyRelationType.SUPPLIER,
                                            request.getContacts(),
                                            categories
                                    )
                            );

                }).orElseThrow(()
                        -> new IllegalArgumentException(
                                "Request supplier can't be null"));
    }


}

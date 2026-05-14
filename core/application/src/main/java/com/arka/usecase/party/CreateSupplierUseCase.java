package com.arka.usecase.supplier;

import com.arka.dto.in.CreateCompanyIn;

import com.arka.gateway.repository.party.SupplierGateway;
import com.arka.model.Company;
import com.arka.enums.CompanyRelationType;
import com.arka.factory.CompanyFactory;
import com.arka.model.product.ProductCategory;
import com.arka.service.ProductCategoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateSupplierUseCase {

    private final ProductCategoryService productCategoryService;
    private final SupplierGateway supplierGateway;
    private final CompanyFactory companyFactory = new CompanyFactory();



    public Company execute(CreateCompanyIn input) {

        NullValidator.validate(input, "input");

        return Optional.ofNullable(request)
                .map(r -> {

                    Set<ProductCategory> categories = request
                            .getSlugProductCategories()
                            .stream()
                            .map(category ->
                                    productCategoryService.getBySlug(category.slug()))
                            .collect(Collectors.toSet());

                    return supplierGateway
                            .createCompany(companyFactory
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

package com.arka.usecase.party;

import com.arka.dto.out.CompanyOut;
import com.arka.mapper.CompanyMapper;
import com.arka.mapper.CompanyMapperImpl;
import com.arka.model.Company;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.product.ProductCategoryGateway;
import com.arka.gateway.repository.party.SupplierGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListSuppliersByCategoryUseCase {

    private final ProductCategoryGateway productCategoryGateway;
    private final SupplierGateway supplierGateway;
    private final CompanyMapper companyMapper =
            new CompanyMapperImpl();

    public List<CompanyOut> execute(Long productCategoryId) {

        validateExistingCategory(productCategoryId);

        List<Company> foundSuppliers =
                supplierGateway.getSuppliersByProductCategoryId(productCategoryId);

        return foundSuppliers.stream()
                .map(companyMapper::toOut)
                .toList();
    }

    private void validateExistingCategory(Long categoryId){
        productCategoryGateway.findById(categoryId).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Category with id %d not found", categoryId)));
    }
}

package com.arka.usecase.party;

import com.arka.dto.out.CompanyOut;
import com.arka.mapper.CompanyMapper;
import com.arka.mapper.CompanyMapperImpl;
import com.arka.model.Company;
import com.arka.gateway.repository.party.SupplierGateway;
import com.arka.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListSuppliersByCategoryUseCase {

    private final ProductCategoryService productCategoryService;
    private final SupplierGateway supplierGateway;
    private final CompanyMapper companyMapper =
            new CompanyMapperImpl();

    public List<CompanyOut> execute(Long productCategoryId) {

        productCategoryService.findById(productCategoryId);

        List<Company> foundSuppliers =
                supplierGateway.getSuppliersByProductCategoryId(productCategoryId);

        return foundSuppliers.stream()
                .map(companyMapper::toOut)
                .toList();
    }
}

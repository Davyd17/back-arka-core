package com.arka.party;

import com.arka.entities.Company;
import com.arka.party.dto.CompanyOut;
import com.arka.party.mapper.CompanyMapper;
import com.arka.party.gateway.SupplierGateway;
import com.arka.party.mapper.CompanyMapperImpl;
import com.arka.product.service.ProductCategoryService;
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

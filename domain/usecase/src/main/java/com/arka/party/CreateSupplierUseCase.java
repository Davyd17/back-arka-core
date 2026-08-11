package com.arka.party;

import com.arka.entities.Company;
import com.arka.entities.information.Contact;
import com.arka.entities.product.ProductCategory;
import com.arka.party.dto.CreateSupplierIn;

import com.arka.party.dto.CompanyOut;
import com.arka.party.gateway.SupplierGateway;
import com.arka.party.mapper.CompanyMapper;
import com.arka.enums.CompanyRelationType;
import com.arka.party.mapper.CompanyMapperImpl;
import com.arka.party.service.ContactService;
import com.arka.product.service.ProductCategoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class CreateSupplierUseCase {

    private final ProductCategoryService categoryService;

    private final SupplierGateway supplierGateway;

    private final CompanyMapper companyMapper =
            Mappers.getMapper(CompanyMapper.class);

    public CompanyOut execute(CreateSupplierIn input) {

        NullValidator.validate(input, "input");

        List<ProductCategory> foundCategories =
                categoryService.findAllByIds(input.productCategoryIds());


        Company newSupplier = Company.createSupplier(
                input.name(),
                CompanyRelationType.SUPPLIER,
                foundCategories);

        return companyMapper.toOut(supplierGateway.createCompany(newSupplier));
    }

}

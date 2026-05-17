package com.arka.usecase.party;

import com.arka.dto.in.CreateSupplierIn;

import com.arka.dto.out.CompanyOut;
import com.arka.gateway.party.SupplierGateway;
import com.arka.mapper.CompanyMapper;
import com.arka.mapper.CompanyMapperImpl;
import com.arka.model.Company;
import com.arka.enums.CompanyRelationType;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import com.arka.service.ContactService;
import com.arka.service.ProductCategoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateSupplierUseCase {

    private final ProductCategoryService categoryService;
    private final ContactService contactService;

    private final SupplierGateway supplierGateway;

    private final CompanyMapper companyMapper =
            new CompanyMapperImpl();

    public CompanyOut execute(CreateSupplierIn input) {

        NullValidator.validate(input, "input");

        List<Contact> foundContacts =
                contactService.findAllByIds(input.contactIds());

        List<ProductCategory> foundCategories =
                categoryService.findAllByIds(input.productCategoryIds());


        Company newSupplier = Company.createSupplier(
                input.name(),
                CompanyRelationType.SUPPLIER,
                foundContacts,
                foundCategories);

        return companyMapper.toOut(supplierGateway.createCompany(newSupplier));
    }

}

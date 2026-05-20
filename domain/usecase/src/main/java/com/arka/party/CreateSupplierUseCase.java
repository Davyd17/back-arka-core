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

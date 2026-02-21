package com.arka.factory;

import com.arka.model.Company;
import com.arka.enums.CompanyRelationType;
import com.arka.model.information.Address;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyFactory {

    public Company createCompanyWithFullInfo(
            String name,
            CompanyRelationType relation,
            List<Contact> contacts,
            Set<ProductCategory> productCategories
    ) {

        List<Contact> newContacts = contacts.stream()
                .map(c -> Contact.create(
                        c.getName(),
                        c.getLastName(),
                        c.getPosition(),
                        c.getEmail(),
                        c.getAddresses()
                                .stream()
                                .map(Address::createSupplierAddress)
                                .toList(),
                        c.getPhoneNumbers(),
                        c.getUserId()
                ))
                .toList();

        Set<ProductCategory> addCategories =
                productCategories
                        .stream()
                        .map(pc ->
                                ProductCategory.builder()
                                        .id(pc.id())
                                        .build()
                        ).collect(Collectors.toSet());

        return Company.create(
                name,
                relation,
                newContacts,
                addCategories
        );

    }
}

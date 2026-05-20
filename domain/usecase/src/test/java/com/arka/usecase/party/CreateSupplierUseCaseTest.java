package com.arka.usecase.party;

import com.arka.party.CreateSupplierUseCase;
import com.arka.party.dto.CreateSupplierIn;
import com.arka.enums.CompanyRelationType;
import com.arka.gateway.party.SupplierGateway;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import com.arka.party.service.ContactService;
import com.arka.product.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSupplierUseCaseTest {

    @Mock
    private ProductCategoryService categoryService;

    @Mock
    private ContactService contactService;

    @Mock
    private SupplierGateway supplierGateway;

    @InjectMocks
    private CreateSupplierUseCase useCase;

    private Contact buildContact(Long id) {
        return new Contact(id, "John", "Doe", "CEO", "john@test.com",
                new ArrayList<>(), new ArrayList<>(), true, null);
    }

    // --- input validation ---

    @Test
    void shouldThrowWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null));
    }

    // --- supplier creation ---

    @Test
    void shouldCreateSupplierWithCorrectContactsAndCategories() {
        List<Contact> contacts = List.of(buildContact(1L));
        List<ProductCategory> categories = List.of(ProductCategory.create("Electronics"));

        CreateSupplierIn input = new CreateSupplierIn(
                "Supplier", Set.of(1L), Set.of(1L));

        when(contactService.findAllByIds(any())).thenReturn(contacts);
        when(categoryService.findAllByIds(any())).thenReturn(categories);
        when(supplierGateway.createCompany(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(supplierGateway).createCompany(argThat(company ->
                company.getContacts().size() == 1 &&
                        company.getProductCategories().size() == 1 &&
                        company.getRelation().equals(CompanyRelationType.SUPPLIER)
        ));
    }

    // --- persistence ---

    @Test
    void shouldAlwaysCallGatewayCreate() {
        when(contactService.findAllByIds(any())).thenReturn(List.of(buildContact(1L)));
        when(categoryService.findAllByIds(any())).thenReturn(
                List.of(ProductCategory.create("Electronics")));
        when(supplierGateway.createCompany(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(new CreateSupplierIn("Supplier", Set.of(1L), Set.of(1L)));

        verify(supplierGateway).createCompany(any());
    }
}

package com.arka.model.company;

import com.arka.entities.Company;
import com.arka.entities.information.Contact;
import com.arka.entities.product.ProductCategory;
import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    private Company company;
    private Contact contact;
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        contact = buildContact(1L);
        category = buildCategory(1L);

        company = Company.createSupplier(
                "Test Company",
                CompanyRelationType.CUSTOMER,
                List.of(contact),
                List.of(category)
        );
    }

    private Contact buildContact(Long id) {
        return new Contact(id, "John", "Doe", "CEO",
                "john@test.com", new ArrayList<>(), new ArrayList<>(), true, 1L);
    }

    private ProductCategory buildCategory(Long id) {
        return new ProductCategory(id, "Category " + id, "category-" + id);
    }

    // --- create ---

    @Test
    void shouldThrowWhenContactsIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, null, List.of()));
    }

    @Test
    void shouldThrowWhenContactsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of(), List.of()));
    }

    @Test
    void shouldThrowWhenCategoriesIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of(), null));
    }

    @Test
    void shouldThrowWhenCategoriesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of(), List.of()));
    }

    // --- addContact ---

    @Test
    void shouldAddContactSuccessfully() {
        company.addContact(buildContact(2L));

        assertEquals(2, company.getContacts().size());
    }

    @Test
    void shouldThrowWhenAddingDuplicateContact() {
        assertThrows(AlreadyExistsException.class, () ->
                company.addContact(buildContact(1L)));
    }

    // --- removeContact ---

    @Test
    void shouldRemoveContactSuccessfully() {
        company.removeContact(1L);

        assertTrue(company.getContacts().isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistentContact() {
        assertThrows(NotFoundException.class, () ->
                company.removeContact(99L));
    }

    // --- addProductCategory ---

    @Test
    void shouldAddProductCategorySuccessfully() {
        company.addProductCategory(buildCategory(2L));

        assertEquals(2, company.getProductCategories().size());
    }

    @Test
    void shouldThrowWhenAddingDuplicateProductCategory() {
        assertThrows(AlreadyExistsException.class, () ->
                company.addProductCategory(buildCategory(1L)));
    }

    // --- removeProductCategory ---

    @Test
    void shouldRemoveProductCategorySuccessfully() {
        company.removeProductCategory(1L);
        assertTrue(company.getProductCategories().isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistentProductCategory() {
        assertThrows(NotFoundException.class, () ->
                company.removeProductCategory(99L));
    }
}
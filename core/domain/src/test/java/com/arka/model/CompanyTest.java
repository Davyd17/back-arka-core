package com.arka.model;

import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    private Company company;
    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = buildContact(1L);

        company = Company.create(
                "Test Company",
                CompanyRelationType.CUSTOMER,
                new ArrayList<>(List.of(contact))
        );
    }

    private Contact buildContact(Long id) {
        return new Contact(id, "John", "Doe", "CEO",
                "john@test.com", new ArrayList<>(), new ArrayList<>(), null, true, 1L);
    }

    private ProductCategory buildCategory(Long id) {
        return new ProductCategory(id, "Category " + id, "category-" + id);
    }

    // --- create ---

    @Test
    void shouldThrowWhenContactsIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.create("Test", CompanyRelationType.CUSTOMER, null));
    }

    @Test
    void shouldThrowWhenContactsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.create("Test", CompanyRelationType.CUSTOMER, new ArrayList<>()));
    }

    @Test
    void shouldInitializeProductCategoriesAsEmptySet() {
        assertNotNull(company.getProductCategories());
        assertTrue(company.getProductCategories().isEmpty());
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
        company.addProductCategory(buildCategory(1L));

        assertEquals(1, company.getProductCategories().size());
    }

    @Test
    void shouldThrowWhenAddingDuplicateProductCategory() {
        company.addProductCategory(buildCategory(1L));

        assertThrows(AlreadyExistsException.class, () ->
                company.addProductCategory(buildCategory(1L)));
    }

    // --- removeProductCategory ---

    @Test
    void shouldRemoveProductCategorySuccessfully() {
        company.addProductCategory(buildCategory(1L));
        company.removeProductCategory(1L);

        assertTrue(company.getProductCategories().isEmpty());
    }

    @Test
    void shouldThrowWhenRemovingNonExistentProductCategory() {
        assertThrows(NotFoundException.class, () ->
                company.removeProductCategory(99L));
    }
}
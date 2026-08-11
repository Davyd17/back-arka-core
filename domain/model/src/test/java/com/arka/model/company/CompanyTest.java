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
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        category = buildCategory(1L);

        company = Company.createSupplier(
                "Test Company",
                CompanyRelationType.CUSTOMER,
                List.of(category)
        );
    }

    private ProductCategory buildCategory(Long id) {
        return new ProductCategory(id, "Category " + id, "category-" + id);
    }

    // --- create ---

    @Test
    void shouldThrowWhenContactsIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of()));
    }

    @Test
    void shouldThrowWhenContactsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of()));
    }

    @Test
    void shouldThrowWhenCategoriesIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, null));
    }

    @Test
    void shouldThrowWhenCategoriesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Company.createSupplier("Test", CompanyRelationType.CUSTOMER, List.of()));
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

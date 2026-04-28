package com.arka.model.product;

import jdk.jfr.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCategoryTest {

    private Category category;

    @Test
    void shouldSetASlugByCreatingNewCategory(){

        ProductCategory newCategory =
                ProductCategory.create("Category Test");

        assertEquals("category-test", newCategory.getSlug());
    }

}
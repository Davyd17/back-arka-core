package com.arka.product;

import com.arka.entities.product.Product;
import com.arka.entities.product.ProductCategory;
import com.arka.product.category.ProductCategoryMapper;
import com.arka.product.category.ProductCategoryMapperImpl;
import com.arka.product.category.ProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        ProductServiceAdapter.class,
        ProductEntityMapperImpl.class,
        ProductCategoryMapperImpl.class
})
@ActiveProfiles("test")
class ProductServiceAdapterTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Autowired
    private ProductServiceAdapter productServiceAdapter;


    @Test
    void shouldCreateProduct() {

        // given
        ProductCategory category = categoryMapper.toDomain(
                categoryRepository.findById(1L).orElseThrow());

        Product product = Product.create(
                "TEST-PR-001",
                "Testing Product",
                "",
                BigDecimal.valueOf(149.99),
                category);

        // when
        Product createdProduct = productServiceAdapter.create(product);

        // then
        assertNotNull(createdProduct.getId(), "Created product should have a generated ID");

        Optional<ProductEntity> foundEntity = productRepository.findById(createdProduct.getId());
        assertTrue(foundEntity.isPresent(), "Product should be persisted in the database");

        ProductEntity entity = foundEntity.get();
        assertEquals("TEST-PR-001", entity.getSku());
        assertEquals("Testing Product", entity.getName());
        assertEquals(0, BigDecimal.valueOf(149.99).compareTo(entity.getBasePrice()));
        assertTrue(entity.isActive());
        assertNotNull(entity.getCategory(), "Product should maintain relationship with its Category");
        assertEquals(1L, entity.getCategory().getId());
    }

}
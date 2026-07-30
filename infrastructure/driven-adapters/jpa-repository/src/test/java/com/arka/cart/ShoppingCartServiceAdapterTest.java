package com.arka.cart;

import com.arka.cart.item.ShoppingCartItemEntityMapper;
import com.arka.cart.item.ShoppingCartItemEntityMapperImpl;
import com.arka.entities.cart.ShoppingCart;
import com.arka.entities.product.Product;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({ShoppingCartServiceAdapter.class,
        ProductEntityMapperImpl.class,
        ShoppingCartEntityMapperImpl.class,
        ShoppingCartItemEntityMapperImpl.class})
class ShoppingCartServiceAdapterTest {

    @Autowired
    private ShoppingCartServiceAdapter shoppingCartServiceAdapter;

    @Autowired
    private ShoppingCartJpaRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Test
    void shouldSaveShoppingCartAndMaintainBidirectionalRelationship() {
        // given
        ShoppingCart domainCart = ShoppingCart.create(1L);

        Product product = productEntityMapper.toDomain(
                productRepository.findById(1L).orElseThrow());

        domainCart.addItem(product, 20);

        // when
        ShoppingCart savedCart = shoppingCartServiceAdapter.save(domainCart);

        // then
        assertNotNull(savedCart);

        // Verify it actually persisted in the DB through the repository
        Optional<ShoppingCartEntity> dbEntity =
                repository.findFirstByUserIdOrderByCreatedAtDesc(1L);
        assertTrue(dbEntity.isPresent());

        //Verify items were linked to the shopping cart
        assertFalse(dbEntity.get().getItems().isEmpty());

        // Verify that the helper method linked the parent back into the items
        dbEntity.get().getItems().forEach(item -> {
            assertNotNull(item.getShoppingCart(),
                    "The bidirectional relationship back to the shopping cart should be set.");
            assertEquals(dbEntity.get().getId(), item.getShoppingCart().getId());
        });
    }


}
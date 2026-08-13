package com.arka.cart;

import com.arka.cart.item.ShoppingCartItemEntityMapperImpl;
import com.arka.entities.cart.ShoppingCart;
import com.arka.entities.information.Contact;
import com.arka.entities.product.Product;
import com.arka.enums.ShoppingCartStatus;
import com.arka.factory.ContactTestDataFactory;
import com.arka.factory.ProductTestDataFactory;
import com.arka.factory.ShoppingCartTestDataFactory;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.contact.ContactEntity;
import com.arka.information.contact.ContactEntityMapper;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.product.ProductEntity;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({ShoppingCartServiceAdapter.class,
        ProductEntityMapperImpl.class,
        ShoppingCartEntityMapperImpl.class,
        ShoppingCartItemEntityMapperImpl.class,
        ContactEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PhoneNumberEntityMapperImpl.class})
class ShoppingCartServiceAdapterTest {

    @Autowired
    private ShoppingCartServiceAdapter shoppingCartServiceAdapter;

    @Autowired
    private ShoppingCartJpaRepository repository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Autowired
    private ContactEntityMapper contactEntityMapper;

    @Autowired
    private TestEntityManager entityManager;

    private ContactTestDataFactory contactTestDataFactory;
    private ProductTestDataFactory productTestDataFactory;
    private ShoppingCartTestDataFactory shoppingCartTestDataFactory;

    @BeforeEach
    void setUp() {
        contactTestDataFactory = new ContactTestDataFactory(entityManager);
        productTestDataFactory = new ProductTestDataFactory(entityManager);
        shoppingCartTestDataFactory = new ShoppingCartTestDataFactory(entityManager);
    }

    @Test
    void shouldSaveShoppingCartAndMaintainBidirectionalRelationship() {
        // given
        ContactEntity contactEntity = contactTestDataFactory.createContact();
        Contact domainContact = contactEntityMapper.toDomain(contactEntity);

        ProductEntity productEntity = productTestDataFactory.createProduct();
        Product product = productEntityMapper.toDomain(productEntity);

        ShoppingCart domainCart = ShoppingCart.create(domainContact);
        domainCart.addItem(product, 20);

        // when
        ShoppingCart savedCart = shoppingCartServiceAdapter.save(domainCart);

        // then
        assertNotNull(savedCart);

        // Verify it actually persisted in the DB through the repository
        Optional<ShoppingCartEntity> dbEntity =
                repository.findFirstByContactIdOrderByCreatedAtDesc(contactEntity.getId());
        assertTrue(dbEntity.isPresent());

        // Verify items were linked to the shopping cart
        assertFalse(dbEntity.get().getItems().isEmpty());

        // Verify that the helper method linked the parent back into the items
        dbEntity.get().getItems().forEach(item -> {
            assertNotNull(item.getShoppingCart(),
                    "The bidirectional relationship back to the shopping cart should be set.");
            assertEquals(dbEntity.get().getId(), item.getShoppingCart().getId());
        });
    }

    @Test
    void shouldReturnLastCreatedCartForContact() {
        // Given
        ContactEntity contactEntity = contactTestDataFactory.createContact();

        // Populate an initial cart, flush, and then persist the latest cart
        shoppingCartTestDataFactory.createShoppingCart(contactEntity);
        entityManager.flush();
        ShoppingCartEntity latestCart = shoppingCartTestDataFactory.createShoppingCart(contactEntity);

        // When
        Optional<ShoppingCart> result =
                shoppingCartServiceAdapter.getLastCreatedCart(contactEntity.getId());

        // Then
        assertThat(result).isPresent();

        ShoppingCart cart = result.get();
        assertThat(cart.getId()).isEqualTo(latestCart.getId());
        assertThat(cart.getStatus()).isEqualTo(ShoppingCartStatus.ACTIVE);
        assertThat(cart.getContact()).isNotNull();
        assertThat(cart.getContact().getId()).isEqualTo(contactEntity.getId());
        assertThat(cart.getContact().getEmail()).isEqualTo(contactEntity.getEmail());
    }

    @Test
    void shouldReturnEmptyWhenNoCartExistsForContact() {
        // Given
        Long nonExistingOwnerId = 999L;

        // When
        Optional<ShoppingCart> result =
                shoppingCartServiceAdapter.getLastCreatedCart(nonExistingOwnerId);

        // Then
        assertThat(result).isEmpty();
    }

}

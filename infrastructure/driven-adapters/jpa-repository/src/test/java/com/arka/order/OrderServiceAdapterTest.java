package com.arka.order;

import com.arka.company.CompanyEntityMapperImpl;
import com.arka.entities.information.Contact;
import com.arka.entities.order.Order;
import com.arka.entities.order.OrderItem;
import com.arka.entities.product.Product;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.factory.ContactTestDataFactory;
import com.arka.factory.ProductTestDataFactory;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.contact.ContactEntity;
import com.arka.information.contact.ContactEntityMapper;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.order.item.OrderItemEntityMapperImpl;
import com.arka.product.ProductEntity;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.category.ProductCategoryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({OrderServiceAdapter.class,
        OrderEntityMapperImpl.class,
        OrderItemEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ProductCategoryMapperImpl.class,
        CompanyEntityMapperImpl.class,
        ContactEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PhoneNumberEntityMapperImpl.class})
@ActiveProfiles("test")
class OrderServiceAdapterTest {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Autowired
    private ContactEntityMapper contactEntityMapper;

    @Autowired
    private OrderServiceAdapter orderServiceAdapter;

    @Autowired
    private TestEntityManager entityManager;

    private ContactTestDataFactory contactTestDataFactory;
    private ProductTestDataFactory productTestDataFactory;

    @BeforeEach
    void setUp() {
        contactTestDataFactory = new ContactTestDataFactory(entityManager);
        productTestDataFactory = new ProductTestDataFactory(entityManager);
    }

    @Test
    void shouldSaveOrder() {
        // given
        ContactEntity contactEntity = contactTestDataFactory.createContact();
        Contact domainContact = contactEntityMapper.toDomain(contactEntity);

        Order order = Order.create(null, OrderType.SALES, domainContact);

        // when
        Order saved = orderServiceAdapter.save(order);

        // then
        assertNotNull(saved.getId());

        Optional<OrderEntity> found = orderRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(OrderStatus.PENDING, found.get().getStatus());
    }

    @Test
    void shouldMaintainOrderItemBidirectionalRelationshipWhenSaved() {
        // given
        ContactEntity contactEntity = contactTestDataFactory.createContact();
        Contact domainContact = contactEntityMapper.toDomain(contactEntity);

        ProductEntity productEntity = productTestDataFactory.createProduct();
        Product domainProduct = productEntityMapper.toDomain(productEntity);

        Order order = Order.create(null, OrderType.SALES, domainContact);
        order.addItem(OrderItem.create(domainProduct, 2));

        // when
        Order saved = orderServiceAdapter.save(order);

        // then
        assertFalse(saved.getItems().isEmpty());

        OrderEntity foundEntity = orderRepository.findById(saved.getId()).orElseThrow();

        assertFalse(foundEntity.getItems().isEmpty());
        foundEntity.getItems().forEach(item ->
                assertNotNull(item.getOrder(),
                        "Each order item should reference back to its order"));
    }}

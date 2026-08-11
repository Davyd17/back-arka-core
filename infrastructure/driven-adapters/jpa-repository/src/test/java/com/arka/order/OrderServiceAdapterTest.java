package com.arka.order;

import com.arka.company.CompanyEntityMapper;
import com.arka.company.CompanyEntityMapperImpl;
import com.arka.company.customer.CustomerRepository;
import com.arka.entities.Company;
import com.arka.entities.information.Contact;
import com.arka.entities.order.Order;
import com.arka.entities.order.OrderItem;
import com.arka.entities.product.Product;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.order.item.OrderItemEntityMapperImpl;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.ProductRepository;
import com.arka.product.category.ProductCategoryMapper;
import com.arka.product.category.ProductCategoryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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
    private ProductRepository productRepository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Autowired
    private OrderServiceAdapter orderServiceAdapter;

    private Contact buildContact(Long id){
        return new Contact(
                id,
                "John",
                "Conor",
                "Test Position",
                null,
                "jhon.conor@example.com",
                new ArrayList<>(),
                new ArrayList<>(),
                true);
    }

    @Test
    void shouldSaveOrder() {

        // given
        Order order = Order.create(null, OrderType.SALES, buildContact(1L));

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
        Product product = productEntityMapper.toDomain(
                productRepository.findById(1L).orElseThrow());

        Order order = Order.create(null, OrderType.SALES, buildContact(1L));
        order.addItem(OrderItem.create(product, 2));

        // when
        Order saved = orderServiceAdapter.save(order);

        // then
        assertFalse(saved.getItems().isEmpty());

        OrderEntity foundEntity = orderRepository.findById(saved.getId()).orElseThrow();

        assertFalse(foundEntity.getItems().isEmpty());
        foundEntity.getItems().forEach(item ->
                assertNotNull(item.getOrder(),
                        "Each order item should reference back to its order"));
    }

}

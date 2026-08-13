package com.arka.shippingdetail;

import com.arka.company.CompanyEntityMapperImpl;
import com.arka.entities.ShippingDetail;
import com.arka.entities.information.Address;
import com.arka.entities.order.Order;
import com.arka.enums.ShippingStatus;
import com.arka.factory.ContactTestDataFactory;
import com.arka.factory.OrderTestDataFactory;
import com.arka.factory.WarehouseTestDataFactory;
import com.arka.information.address.AddressEntity;
import com.arka.information.address.AddressEntityMapper;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.address.AddressEntityRepository;
import com.arka.information.contact.ContactEntity;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.order.OrderEntity;
import com.arka.order.OrderEntityMapper;
import com.arka.order.OrderEntityMapperImpl;
import com.arka.order.OrderRepository;
import com.arka.order.item.OrderItemEntityMapperImpl;
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

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        ShippingDetailServiceAdapter.class,
        ShippingDetailEntityMapperImpl.class,
        OrderEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        ContactEntityMapperImpl.class,
        PhoneNumberEntityMapperImpl.class,
        OrderItemEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ProductCategoryMapperImpl.class,
        CompanyEntityMapperImpl.class
})
@ActiveProfiles("test")
class ShippingDetailServiceAdapterTest {


    @Autowired
    private ShippingDetailRepository shippingDetailRepository;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Autowired
    private AddressEntityMapper addressEntityMapper;

    @Autowired
    private ShippingDetailServiceAdapter shippingDetailServiceAdapter;

    @Autowired
    private TestEntityManager entityManager;

    private ContactTestDataFactory contactTestDataFactory;
    private OrderTestDataFactory orderTestDataFactory;

    @BeforeEach
    void setUp() {
        contactTestDataFactory = new ContactTestDataFactory(entityManager);
        orderTestDataFactory = new OrderTestDataFactory(entityManager);
    }

    @Test
    void shouldSaveShippingDetailAndMaintainRelationships() {
        // given
        ContactEntity contactEntity = contactTestDataFactory.createContact();
        OrderEntity orderEntity = orderTestDataFactory.createOrder(contactEntity, Instant.now());
        Order domainOrder = orderEntityMapper.toDomain(orderEntity);

        AddressEntity originEntity = contactTestDataFactory.createAddress(contactEntity);
        AddressEntity destinationEntity = contactTestDataFactory.createAddress(contactEntity);

        Address originAddress = addressEntityMapper.toDomain(originEntity);
        Address destinationAddress = addressEntityMapper.toDomain(destinationEntity);

        ShippingDetail shippingDetail = ShippingDetail.create(
                "FEDEX",
                "TRACK-999888",
                domainOrder,
                "",
                originAddress,
                destinationAddress);

        // when
        ShippingDetail saved = shippingDetailServiceAdapter.save(shippingDetail);

        // then
        assertNotNull(saved.getId(), "Saved shipping detail should have a generated ID");

        Optional<ShippingDetailEntity> foundEntity =
                shippingDetailRepository.findById(saved.getId());

        assertTrue(foundEntity.isPresent(), "Shipping detail should be persisted in database");

        ShippingDetailEntity entity = foundEntity.get();
        assertEquals("TRACK-999888", entity.getTrackingNumber());
        assertEquals(ShippingStatus.PENDING, entity.getStatus());

        assertEquals(orderEntity.getId(), entity.getOrder().getId());
        assertEquals(originEntity.getId(), entity.getOrigin().getId());
        assertEquals(destinationEntity.getId(), entity.getDestination().getId());
    }}

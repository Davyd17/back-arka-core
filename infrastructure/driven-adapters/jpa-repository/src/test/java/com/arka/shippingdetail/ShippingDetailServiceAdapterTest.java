package com.arka.shippingdetail;

import com.arka.company.CompanyEntityMapperImpl;
import com.arka.entities.ShippingDetail;
import com.arka.entities.information.Address;
import com.arka.entities.order.Order;
import com.arka.enums.ShippingStatus;
import com.arka.information.address.AddressEntityMapper;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.address.AddressEntityRepository;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.order.OrderEntityMapper;
import com.arka.order.OrderEntityMapperImpl;
import com.arka.order.OrderRepository;
import com.arka.order.item.OrderItemEntityMapperImpl;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.category.ProductCategoryMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

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
    private OrderRepository orderRepository;

    @Autowired
    private AddressEntityRepository addressRepository;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Autowired
    private AddressEntityMapper addressEntityMapper;

    @Autowired
    private ShippingDetailServiceAdapter shippingDetailServiceAdapter;


    @Test
    void shouldSaveShippingDetailAndMaintainRelationships() {

        // given
        Order order = orderEntityMapper.toDomain(
                orderRepository.findById(1L).orElseThrow());

        Address originAddress = addressEntityMapper.toDomain(
                addressRepository.findById(1L).orElseThrow());

        Address destinationAddress = addressEntityMapper.toDomain(
                addressRepository.findById(2L).orElseThrow());

        ShippingDetail shippingDetail = ShippingDetail.create(
                "FEDEX",
                "TRACK-999888",
                order,
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

        assertEquals(1L, entity.getOrder().getId());
        assertEquals(1L, entity.getOrigin().getId());
        assertEquals(2L, entity.getDestination().getId());
    }
}